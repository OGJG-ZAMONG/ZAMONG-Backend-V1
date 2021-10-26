package app.jg.og.zamong.controller;

import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.dto.request.ChangePasswordRequest;
import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.Profile;
import app.jg.og.zamong.entity.user.profile.ProfileRepository;
import app.jg.og.zamong.service.user.auth.UserAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends IntegrationTest {

    private User user;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder()
                .name(UserConstant.NAME)
                .email(UserConstant.EMAIL)
                .id(UserConstant.ID)
                .lucyCount(0)
                .password(passwordEncoder.encode(UserConstant.PASSWORD))
                .build());
        Profile profile = profileRepository.save(Profile.builder()
                .uuid(user.getUuid())
                .build());
        user.setProfile(profile);
        userRepository.save(user);
    }

    @Test
    @Transactional
    void user_me_200() throws Exception {
        String accessToken = loginUser();

        mockMvc.perform(get("/user/me")
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, createBearerToken(accessToken))
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    void user_200() throws Exception {
        String accessToken = loginUser();

        mockMvc.perform(get("/user/" + user.getUuid())
                .contentType(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, createBearerToken(accessToken))
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    void user_id_204() throws Exception {
        String accessToken = loginUser();
        String newId = "newUserId";

        CheckIdDuplicationRequest request = CheckIdDuplicationRequest.builder()
                .id(newId)
                .build();

        mockMvc.perform(patch("/user/user-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header(AUTHORIZATION, createBearerToken(accessToken))
        ).andExpect(status().isNoContent());

        assertThat(user.getId()).isEqualTo(newId);
    }

    @Test
    @Transactional
    void password_204() throws Exception {
        String accessToken = loginUser();
        String newPassword = "newUserPassword12!";

        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .oldPassword(UserConstant.PASSWORD)
                .newPassword(newPassword)
                .build();

        mockMvc.perform(patch("/user/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header(AUTHORIZATION, createBearerToken(accessToken))
        ).andExpect(status().isNoContent());
    }

    private String loginUser() {
        return userAuthenticationService.loginUser(LoginUserRequest.builder()
                .userIdentity(user.getEmail())
                .password(UserConstant.PASSWORD)
                .build()).getAccessToken();
    }

    private String createBearerToken(String token) {
        return "Bearer " + token;
    }
}
