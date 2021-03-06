package app.jg.og.zamong.service.user;

import app.jg.og.zamong.dto.response.user.UserInformationResponse;
import app.jg.og.zamong.entity.dream.selldream.buyrequest.SellDreamBuyRequestRepository;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.entity.user.UserRepository;
import app.jg.og.zamong.security.auth.AuthenticationDetails;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import app.jg.og.zamong.service.user.find.UserFindServiceImpl;
import app.jg.og.zamong.util.ShareDreamBuilder;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class UserFindServiceTest extends UnitTest {

    @InjectMocks
    private UserFindServiceImpl userFindService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ShareDreamRepository shareDreamRepository;
    @Mock
    private SecurityContextService securityContextService;
    @Mock
    private SellDreamBuyRequestRepository sellDreamBuyRequestRepository;

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
        given(sellDreamBuyRequestRepository.findByUser(user)).willReturn(Collections.emptyList());

        //when
        UserInformationResponse response = userFindService.queryUserInformation(uuid);

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
        given(sellDreamBuyRequestRepository.findByUser(user)).willReturn(Collections.emptyList());

        //when
        UserInformationResponse response = userFindService.queryMyInformation();

        //then
        assertThat(response.getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getShareDreamCount()).isEqualTo(shareDreamCount);
    }
}
