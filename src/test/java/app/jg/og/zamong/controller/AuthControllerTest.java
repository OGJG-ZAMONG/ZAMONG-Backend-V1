package app.jg.og.zamong.controller;

import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.dto.request.*;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCode;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCodeRepository;
import app.jg.og.zamong.entity.redis.refreshtoken.RefreshToken;
import app.jg.og.zamong.entity.redis.refreshtoken.RefreshTokenRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.ProfileRepository;
import app.jg.og.zamong.exception.ErrorCode;
import app.jg.og.zamong.security.JwtTokenProvider;
import app.jg.og.zamong.service.mail.MailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends IntegrationTest {

    private User user;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationCodeRepository authenticationCodeRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder()
                .name(UserConstant.NAME)
                .email(UserConstant.EMAIL)
                .id(UserConstant.ID)
                .password(passwordEncoder.encode(UserConstant.PASSWORD))
                .build());
    }

    @AfterEach
    void deleteAl() {
        userRepository.deleteAll();
        authenticationCodeRepository.deleteAll();
        profileRepository.deleteAll();
    }

    @Test
    @Transactional
    void auth_userid_duplicate_200() throws Exception {
        String userId = "New_User_Id";

        CheckIdDuplicationRequest request = CheckIdDuplicationRequest.builder()
                .id(userId)
                .build();

        mockMvc.perform(post("/auth/user-id/duplicate")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    void auth_userid_duplicate_400() throws Exception {
        String userId = user.getId();

        CheckIdDuplicationRequest request = CheckIdDuplicationRequest.builder()
                .id(userId)
                .build();

        mockMvc.perform(post("/auth/user-id/duplicate")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void auth_signup_400_1() throws Exception {
        String name = "정지우";
        String id = user.getId();
        String email = user.getEmail();
        String password = "NewPassword@1";
        String authenticationCode = "000000";

        SignUpUserRequest request = SignUpUserRequest.builder()
                .name(name)
                .id(id)
                .email(email)
                .password(password)
                .authenticationCode(authenticationCode)
                .build();

        mockMvc.perform(post("/auth/signup")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andExpect(jsonPath("message").value(ErrorCode.USER_IDENTITY_DUPLICATION.getMessage()));
    }

    @Test
    @Transactional
    void auth_signup_200() throws Exception {
        String name = "정지우";
        String id = "new_zamong_id";
        String email = "newzamong1234@gmail.com";
        String password = "NewPassword@1";
        String authenticationCode = "000000";

        authenticationCodeRepository.save(new AuthenticationCode(email, authenticationCode));

        SignUpUserRequest request = SignUpUserRequest.builder()
                .name(name)
                .id(id)
                .email(email)
                .password(password)
                .authenticationCode(authenticationCode)
                .build();

        mockMvc.perform(post("/auth/signup")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        assertThat(profileRepository.findAll().iterator().hasNext()).isTrue();
    }

    @MockBean
    private MailService mailService;

    @Test
    @Transactional
    void auth_mail_200() throws Exception {
        String email = "newzamong1234@gmail.com";
        EmailAuthenticationRequest request = EmailAuthenticationRequest.builder()
                .address(email)
                .build();

        mockMvc.perform(post("/auth/mail")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    void login_200() throws Exception {
        LoginUserRequest request = LoginUserRequest.builder()
                .userIdentity(user.getEmail())
                .password(UserConstant.PASSWORD)
                .build();

        mockMvc.perform(post("/auth/login")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @Transactional
    void refresh_200() throws Exception {
        String refreshToken = "refreshToken";
        String userId = "user-uuid";

        given(jwtTokenProvider.getUserUuid(refreshToken)).willReturn(userId);
        given(refreshTokenRepository.findById(userId)).willReturn(Optional.of(new RefreshToken(userId, refreshToken)));

        ReIssueTokenRequest request = ReIssueTokenRequest.builder()
                .refreshToken(refreshToken)
                .build();

        mockMvc.perform(post("/auth/refresh")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
