package app.jg.og.zamong.controller;

import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends IntegrationTest {

    private User user;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name(UserConstant.NAME)
                .email(UserConstant.EMAIL)
                .id(UserConstant.ID)
                .password(passwordEncoder.encode(UserConstant.PASSWORD))
                .build();
    }

    @Test
    @Transactional
    void 아이디중복확인_성공() throws Exception {
        CheckIdDuplicationRequest request = CheckIdDuplicationRequest.builder()
                .id(user.getId())
                .build();

        mockMvc.perform(post("/auth/user-id/duplicate")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    void 아이디중복확인_실패() throws Exception {
        userRepository.save(user);
        CheckIdDuplicationRequest request = CheckIdDuplicationRequest.builder()
                .id(user.getId())
                .build();

        mockMvc.perform(post("/auth/user-id/duplicate")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}
