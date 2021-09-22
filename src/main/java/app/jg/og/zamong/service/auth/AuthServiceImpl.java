package app.jg.og.zamong.service.auth;

import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    @Override
    public User registerUser(SignUpUserRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent((user) -> {
                    throw new RuntimeException();
                });

        return this.userRepository.save(User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .id(request.getId())
                .password(passwordEncoder.encode(request.getPassword()))
                .build());
    }

    @Override
    public IssueTokenResponse loginUser(LoginUserRequest request) {
        User user = verifyUser(request);
        return generateIssueTokenResponse(user);
    }

    private User verifyUser(LoginUserRequest request) throws RuntimeException {
        return userRepository.findByEmailOrId(request.getUserIdentity())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .orElseThrow(RuntimeException::new);
    }

    private IssueTokenResponse generateIssueTokenResponse(User user) {
        return IssueTokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(user.getUuid().toString()))
                .refreshToken(jwtTokenProvider.generateRefreshToken(user.getUuid().toString()))
                .build();
    }
}
