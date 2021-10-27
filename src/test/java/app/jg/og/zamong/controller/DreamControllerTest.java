package app.jg.og.zamong.controller;

import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.dream.DreamCommentRequest;
import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamRepository;
import app.jg.og.zamong.entity.dream.comment.CommentRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.Profile;
import app.jg.og.zamong.service.user.auth.UserAuthenticationService;
import app.jg.og.zamong.util.ShareDreamBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DreamControllerTest extends IntegrationTest {

    private User user;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DreamRepository dreamRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder()
                .name(UserConstant.NAME)
                .email(UserConstant.EMAIL)
                .id(UserConstant.ID)
                .password(passwordEncoder.encode(UserConstant.PASSWORD))
                .build());
        Profile profile = profileRepository.save(Profile.builder()
                .uuid(user.getUuid())
                .build());
        user.setProfile(profile);
        userRepository.save(user);
    }

    @AfterEach
    void deleteAl() {
        userRepository.deleteAll();
        dreamRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    @Transactional
    void dream_comment_200() throws Exception {
        String content = "comment content";
        String accessToken = loginUser();

        Dream dream = dreamRepository.save(ShareDreamBuilder.build(user));

        DreamCommentRequest request = DreamCommentRequest.builder()
                .pComment(null)
                .content(content).build();

        mockMvc.perform(post("/dream/" + dream.getUuid() + "/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header(AUTHORIZATION, createBearerToken(accessToken))
        ).andExpect(status().isCreated());
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
