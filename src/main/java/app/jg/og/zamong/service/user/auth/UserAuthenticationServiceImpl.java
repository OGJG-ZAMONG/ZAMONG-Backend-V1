package app.jg.og.zamong.service.user.auth;

import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.ReIssueTokenRequest;
import app.jg.og.zamong.dto.response.IssueTokenResponse;
import app.jg.og.zamong.entity.redis.refreshtoken.RefreshToken;
import app.jg.og.zamong.entity.redis.refreshtoken.RefreshTokenRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.business.BadUserInformationException;
import app.jg.og.zamong.exception.business.UnauthorizedTokenException;
import app.jg.og.zamong.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    JwtTokenProvider jwtTokenProvider;
    PasswordEncoder passwordEncoder;

    RefreshTokenRepository refreshTokenRepository;
    UserRepository userRepository;

    @Override
    public IssueTokenResponse loginUser(LoginUserRequest request) {
        User user = verifyUser(request);
        IssueTokenResponse response = generateIssueTokenResponse(user);

        refreshTokenRepository.save(new RefreshToken(user.getUuid().toString(), response.getRefreshToken()));

        return response;
    }

    private User verifyUser(LoginUserRequest request) throws RuntimeException {
        return userRepository.findByEmailOrId(request.getUserIdentity())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .orElseThrow(() -> new BadUserInformationException("잘못된 아이디 혹은 비밀번호입니다"));
    }

    private IssueTokenResponse generateIssueTokenResponse(User user) {
        return IssueTokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(user.getUuid().toString()))
                .refreshToken(jwtTokenProvider.generateRefreshToken(user.getUuid().toString()))
                .build();
    }

    @Override
    public IssueTokenResponse refreshToken(ReIssueTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        String userId = jwtTokenProvider.getUserUuid(refreshToken);

        refreshTokenRepository.findById(userId)
                .filter(rt -> rt.getRefreshToken().equals(refreshToken))
                .orElseThrow(() -> new UnauthorizedTokenException("인증되지 않은 토큰입니다"));

        return IssueTokenResponse.builder()
                .accessToken(jwtTokenProvider.generateAccessToken(userId))
                .refreshToken(refreshToken)
                .build();
    }
}
