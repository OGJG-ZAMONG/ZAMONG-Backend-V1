package app.jg.og.zamong.controller;

import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.dto.request.SignUpUserRequest;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCode;
import app.jg.og.zamong.entity.redis.authenticationcode.AuthenticationCodeRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

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

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder()
                .name(UserConstant.NAME)
                .email(UserConstant.EMAIL)
                .id(UserConstant.ID)
                .password(passwordEncoder.encode(UserConstant.PASSWORD))
                .build());
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
}
