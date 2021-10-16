package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.response.UserInformationResponse;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.util.ShareDreamBuilder;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class UserServiceTest extends UnitTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ShareDreamRepository shareDreamRepository;

    static private User user;

    @BeforeAll
    static void setUp() {
        user = UserBuilder.build();
    }

    @Test
    void 유저기본정보_가져오기_성공() {
        //given
        List<ShareDream> shareDreams = List.of(ShareDreamBuilder.build(user));
        Integer shareDreamCount = shareDreams.size();

        given(shareDreamRepository.findByUser(user)).willReturn(shareDreams);

        //when
        UserInformationResponse response = userService.queryUserInformation(user);

        //then
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getShareDreamCount()).isEqualTo(shareDreamCount);
    }
}
