package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.request.*;
import app.jg.og.zamong.dto.request.dream.LeaveUserRequest;
import app.jg.og.zamong.dto.response.StringResponse;
import app.jg.og.zamong.entity.redis.findpasswordtoken.FindPasswordToken;
import app.jg.og.zamong.entity.redis.findpasswordtoken.FindPasswordTokenRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.Profile;
import app.jg.og.zamong.entity.user.profile.ProfileRepository;
import app.jg.og.zamong.exception.business.BadUserInformationException;
import app.jg.og.zamong.exception.business.UserIdentityDuplicationException;
import app.jg.og.zamong.exception.business.UserNotFoundException;
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
                    throw new UserIdentityDuplicationException("?????? ???????????? ??????????????????");
                });

        user.setId(request.getId());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void modifyPassword(ChangePasswordRequest request) {
        User user = securityContextService.getPrincipal().getUser();

        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadUserInformationException("????????? ?????????????????????");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        findPasswordTokenRepository.findById(request.getEmail())
                .filter(token -> token.getFindPasswordToken().equals(request.getChangePasswordToken()))
                .orElseThrow(() -> new BadUserInformationException("????????? ???????????? ?????? ??????"));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("?????? ??? ?????? ?????????"));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void leaveUser(LeaveUserRequest request) {
        User user = securityContextService.getPrincipal().getUser();

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadUserInformationException("????????? ?????????????????????");
        }

        userRepository.delete(user);
    }

    private static final String ZAMONG_URL = "https://zamong.org/changepw";
    private final MailService mailService;

    @Override
    public StringResponse sendFindPasswordEmail(String email) {
        String findPasswordToken = UUID.randomUUID().toString();

        findPasswordTokenRepository.save(new FindPasswordToken(email, findPasswordToken));

        String changePasswordPageUrl = ZAMONG_URL + "?token=" + findPasswordToken + "&email=" + email;

        SendSimpleMailRequest request = SendSimpleMailRequest.builder()
                .address(email)
                .title("ZAMONG ???????????? ?????? ??????")
                .content(changePasswordPageUrl + "\n??? ????????? ????????? ??????????????? ??????????????????")
                .build();

        mailService.sendSimpleEmail(request);

        return new StringResponse("???????????? ?????? ????????? ??????????????? ???????????????");
    }
}
