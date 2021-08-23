package app.jg.og.zamong.service.auth;

import app.jg.og.zamong.dto.request.UserSignUpRequest;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public User createUser(UserSignUpRequest request) {
        return this.userRepository.save(User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .id(request.getId())
                .password(request.getPassword())
                .build());
    }
}
