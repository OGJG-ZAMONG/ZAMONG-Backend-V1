package app.jg.og.zamong.service.user;

import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.dto.response.UserInformationResponse;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.ProfileRepository;
import app.jg.og.zamong.security.auth.AuthenticationDetails;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.service.file.FileSaveService;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import app.jg.og.zamong.util.ShareDreamBuilder;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceTest extends UnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ShareDreamRepository shareDreamRepository;
    @Mock
    private SecurityContextService securityContextService;
    @Mock
    private FileSaveService fileSaveService;
    @Mock
    private ProfileRepository profileRepository;

    static private User user;

    @BeforeAll
    static void setUp() {
        user = UserBuilder.build();
    }

    @Test
    void 유저기본정보_가져오기_성공() {
        //given
        String uuid = user.getUuid().toString();
        List<ShareDream> shareDreams = List.of(ShareDreamBuilder.build(user));
        Integer shareDreamCount = shareDreams.size();

        given(userRepository.findById(UUID.fromString(uuid))).willReturn(Optional.of(user));
        given(shareDreamRepository.findByUser(user)).willReturn(shareDreams);

        //when
        UserInformationResponse response = userService.queryUserInformation(uuid);

        //then
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getShareDreamCount()).isEqualTo(shareDreamCount);
    }

    @Test
    void 내정보_가져오기_성공() {
        //given
        String uuid = user.getUuid().toString();
        List<ShareDream> shareDreams = List.of(ShareDreamBuilder.build(user));
        Integer shareDreamCount = shareDreams.size();

        given(securityContextService.getPrincipal()).willReturn(new AuthenticationDetails(user));
        given(shareDreamRepository.findByUser(user)).willReturn(shareDreams);

        //when
        UserInformationResponse response = userService.queryMyInformation();

        //then
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getShareDreamCount()).isEqualTo(shareDreamCount);
    }

    @Test
    void 프로필_수정_성공() {
        //given
        MultipartFile file = new MockMultipartFile("file_name", "file_content".getBytes(StandardCharsets.UTF_8));

        String directory = "user";

        given(fileSaveService.queryHostName()).willReturn(UserConstant.PROFILE_HOST);
        given(fileSaveService.saveFile(file, directory)).willReturn(UserConstant.PROFILE_PATH);
        given(securityContextService.getPrincipal()).willReturn(new AuthenticationDetails(user));

        //when
        userService.modifyProfile(file);

        //then
        verify(profileRepository, times(1)).save(any());
    }

    @Test
    void 유저아이디_수정_성공() {
        //given
        String modifiedId = "NewId";
        given(userRepository.findById(modifiedId)).willReturn(Optional.empty());
        given(securityContextService.getPrincipal()).willReturn(new AuthenticationDetails(user));

        //when
        CheckIdDuplicationRequest request = CheckIdDuplicationRequest.builder()
                .id(modifiedId).build();
        userService.modifyUserId(request);

        //then
        assertThat(user.getId()).isEqualTo(modifiedId);
    }
}
