package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.dto.response.UserInformationResponse;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.Profile;
import app.jg.og.zamong.entity.user.profile.ProfileRepository;
import app.jg.og.zamong.exception.business.UserIdentityDuplicationException;
import app.jg.og.zamong.exception.business.UserNotFoundException;
import app.jg.og.zamong.service.file.FileSaveService;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ShareDreamRepository shareDreamRepository;
    private final SecurityContextService securityContextService;
    private final FileSaveService fileSaveService;
    private final ProfileRepository profileRepository;

    @Override
    public UserInformationResponse queryUserInformation(String uuid) {
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다"));

        Integer shareDreamCount = shareDreamRepository.findByUser(user).size();
        return UserInformationResponse.of(user, shareDreamCount);
    }

    @Override
    public UserInformationResponse queryMyInformation() {
        User user = securityContextService.getPrincipal().getUser();

        Integer shareDreamCount = shareDreamRepository.findByUser(user).size();
        return UserInformationResponse.of(user, shareDreamCount);
    }

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
    }
}
