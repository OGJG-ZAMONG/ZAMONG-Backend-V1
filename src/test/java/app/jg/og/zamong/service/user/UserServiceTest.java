package app.jg.og.zamong.service.user;

import app.jg.og.zamong.constant.UserConstant;
import app.jg.og.zamong.dto.request.ChangePasswordRequest;
import app.jg.og.zamong.dto.request.CheckIdDuplicationRequest;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.entity.user.profile.ProfileRepository;
import app.jg.og.zamong.exception.business.*;
import app.jg.og.zamong.security.auth.AuthenticationDetails;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.service.file.FileSaveService;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
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
    private SecurityContextService securityContextService;
    @Mock
    private FileSaveService fileSaveService;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    static private User user;

    @BeforeAll
    static void setUp() {
        user = UserBuilder.build();
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
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void 유저아이디_수정_실패() {
        //given
        String modifiedId = "NewId";
        given(userRepository.findById(modifiedId)).willReturn(Optional.of(user));
        given(securityContextService.getPrincipal()).willReturn(new AuthenticationDetails(user));

        //when
        try {
            CheckIdDuplicationRequest request = CheckIdDuplicationRequest.builder()
                    .id(modifiedId).build();
            userService.modifyUserId(request);

            fail("No Error");
        } catch (BusinessException e) {
            //then
            assertThat(e).isInstanceOf(UserIdentityDuplicationException.class);
            verify(userRepository, times(0)).save(any());
        }
    }

    @Test
    void 유저비밀번호_수정_성공() {
        //given
        String oldPassword = user.getPassword();
        String newPassword = "NewPassword";

        given(securityContextService.getPrincipal()).willReturn(new AuthenticationDetails(user));
        given(passwordEncoder.matches(oldPassword, user.getPassword())).willReturn(true);
        given(passwordEncoder.encode(newPassword)).willReturn(newPassword);

        //when
        ChangePasswordRequest request = ChangePasswordRequest.builder()
                .oldPassword(oldPassword)
                .newPassword(newPassword)
                .build();
        userService.modifyPassword(request);

        //then
        assertThat(user.getPassword()).isEqualTo(newPassword);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void 유저비밀번호_수정_실패() {
        //given
        String oldPassword = user.getPassword();
        String newPassword = "NewPassword";

        given(securityContextService.getPrincipal()).willReturn(new AuthenticationDetails(user));
        given(passwordEncoder.matches(oldPassword, user.getPassword())).willReturn(false);

        //when
        try {
            ChangePasswordRequest request = ChangePasswordRequest.builder()
                    .oldPassword(oldPassword)
                    .newPassword(newPassword)
                    .build();
            userService.modifyPassword(request);

            fail("No Error");
        } catch (BusinessException e) {
            //then
            assertThat(e).isInstanceOf(BadUserInformationException.class);
            verify(userRepository, times(0)).save(any());
        }
    }
}
