package app.jg.og.zamong.service.user.find;

import app.jg.og.zamong.dto.response.user.UserGroupResponse;
import app.jg.og.zamong.dto.response.user.UserInformationResponse;
import app.jg.og.zamong.dto.response.user.UserResponse;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.UserNotFoundException;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserFindServiceImpl implements UserFindService {

    private final UserRepository userRepository;
    private final ShareDreamRepository shareDreamRepository;

    private final SecurityContextService securityContextService;

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
    public UserGroupResponse searchUsers(String query) {
        List<User> users = userRepository.findAllByUserId(query);

        return UserGroupResponse.builder()
                .users(users.stream()
                        .map(user -> UserResponse.builder()
                                .uuid(user.getUuid())
                                .id(user.getId())
                                .profile(user.getProfile())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
