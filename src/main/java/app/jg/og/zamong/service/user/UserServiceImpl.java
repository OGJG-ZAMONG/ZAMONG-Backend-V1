package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.request.ChangePasswordRequest;
import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.Profile;
import app.jg.og.zamong.entity.user.profile.ProfileRepository;
import app.jg.og.zamong.exception.business.BadUserInformationException;
import app.jg.og.zamong.exception.business.UserIdentityDuplicationException;
import app.jg.og.zamong.service.file.FileSaveService;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

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

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
