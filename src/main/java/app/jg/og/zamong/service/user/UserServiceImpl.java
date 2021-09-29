package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.response.UserInformationResponse;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserInformationResponse queryUserInformation(String uuid) {
        User user = userRepository.findByUuid(UUID.fromString(uuid))
                .orElseThrow(() -> new UserNotFoundException("해당하는 유저를 찾을 수 없습니다"));
        return UserInformationResponse.builder()
                .uuid(user.getUuid())
                .name(user.getName())
                .email(user.getEmail())
                .id(user.getId())
                .build();
   }
}