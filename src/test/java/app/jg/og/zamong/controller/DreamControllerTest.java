package app.jg.og.zamong.controller;

import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.dto.request.LoginUserRequest;
import app.jg.og.zamong.dto.request.dream.DreamCommentRequest;
import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamRepository;
import app.jg.og.zamong.entity.dream.comment.CommentRepository;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.Profile;
import app.jg.og.zamong.entity.user.profile.ProfileRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private ShareDreamRepository shareDreamRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProfileRepository profileRepository;
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
        dreamRepository.deleteAll();
        shareDreamRepository.deleteAll();
        commentRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void share_dream_timetable_200() throws Exception {
        ShareDream dream = shareDreamRepository.save(ShareDreamBuilder.build(user));

        int year = dream.getSleepDateTime().getYear();
        int month = dream.getSleepDateTime().getMonthValue();

        mockMvc.perform(get("/dream/share/timetable")
                .queryParam("year", Integer.toString(year)).queryParam("month", Integer.toString(month))
                .header(AUTHORIZATION, createBearerToken(loginUser()))
        ).andExpect(status().isOk());
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
