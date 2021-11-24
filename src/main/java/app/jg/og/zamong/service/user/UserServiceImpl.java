package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.request.ChangePasswordRequest;
import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.dto.request.SendSimpleMailRequest;
import app.jg.og.zamong.dto.response.StringResponse;
import app.jg.og.zamong.entity.redis.findpasswordtoken.FindPasswordToken;
import app.jg.og.zamong.entity.redis.findpasswordtoken.FindPasswordTokenRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.Profile;
import app.jg.og.zamong.entity.user.profile.ProfileRepository;
import app.jg.og.zamong.exception.business.BadUserInformationException;
import app.jg.og.zamong.exception.business.UserIdentityDuplicationException;
import app.jg.og.zamong.service.file.FileSaveService;
import app.jg.og.zamong.service.mail.MailService;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final FindPasswordTokenRepository findPasswordTokenRepository;

    private final SecurityContextService securityContextService;
    private final FileSaveService fileSaveService;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void modifyProfile(MultipartFile file) {
        String host = fileSaveService.queryHostName();
        String path = fileSaveService.saveFile(file, "user");

        User user = securityContextService.getPrincipal().getUser();

        profileRepository.save(Profile.builder()
                .uuid(user.getUuid())
                .host(host)
                .path(path)
                .build());
    }

    @Override
    @Transactional
    public void modifyUserId(CheckIdDuplicationRequest request) {
        User user = securityContextService.getPrincipal().getUser();

        userRepository.findById(request.getId())
                .ifPresent((u) -> {
                    throw new UserIdentityDuplicationException("이미 사용중인 아이디입니다");
                });

        user.setId(request.getId());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void modifyPassword(ChangePasswordRequest request) {
        User user = securityContextService.getPrincipal().getUser();

        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadUserInformationException("잘못된 비밀번호입니다");
        }

        findPasswordTokenRepository.findById(user.getUuid().toString())
                .filter(token -> token.getFindPasswordToken().equals(request.getChangePasswordToken()))
                .orElseThrow(() -> new BadUserInformationException("잘못된 비밀번호 변경 토큰"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    private static final String ZAMONG_URL = "https://zamong.org/changepw";
    private final MailService mailService;

    @Override
    public StringResponse sendFindPasswordEmail() {
        User user = securityContextService.getPrincipal().getUser();
        String findPasswordToken = UUID.randomUUID().toString();

        findPasswordTokenRepository.save(new FindPasswordToken(user.getUuid().toString(), findPasswordToken));

        String changePasswordPageUrl = ZAMONG_URL + "?token=" + findPasswordToken;

        SendSimpleMailRequest request = SendSimpleMailRequest.builder()
                .address(user.getEmail())
                .title("ZAMONG 비밀번호 변경 관련")
                .content(changePasswordPageUrl + "\n위 링크로 들어가 비밀번호를 변경해주세요")
                .build();

        mailService.sendSimpleEmail(request);

        return new StringResponse("비밀번호 변경 메일을 성공적으로 보냈습니다");
    }
}
