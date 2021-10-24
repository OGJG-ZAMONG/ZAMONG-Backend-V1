package app.jg.og.zamong.service.dream;

import app.jg.og.zamong.dto.response.ShareDreamGroupResponse;
import app.jg.og.zamong.dto.response.ShareDreamTimeTableResponse;
import app.jg.og.zamong.dto.response.SharedDreamGroupResponse;
import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.dream.sharedream.ShareDreamRepository;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.security.auth.AuthenticationDetails;
import app.jg.og.zamong.service.UnitTest;
import app.jg.og.zamong.service.dream.find.ShareDreamFindServiceImpl;
import app.jg.og.zamong.service.securitycontext.SecurityContextService;
import app.jg.og.zamong.util.ShareDreamBuilder;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

public class ShareDreamFindServiceTest extends UnitTest {

    @InjectMocks
    private ShareDreamFindServiceImpl dreamFindService;

    @Mock
    private ShareDreamRepository shareDreamRepository;
    @Mock
    private SecurityContextService securityContextService;

    @Test
    void 공유된_꿈_목록() {
        //given
        int page= 0;
        int size = 1;

        User user = UserBuilder.build();
        ShareDream shareDream = ShareDreamBuilder.build(user);
        Page<ShareDream> shareDreamPage = new PageImpl(List.of(shareDream));

        given(shareDreamRepository.findByIsSharedIsTrue(any(Pageable.class))).willReturn(shareDreamPage);

        //when
        SharedDreamGroupResponse response = dreamFindService.queryShareDreams(page, size);

        //then
        assertThat(response.getShareDreams().size()).isEqualTo(size);
        assertThat(response.getTotalPage()).isEqualTo(shareDreamPage.getTotalPages());
        assertThat(response.getTotalSize()).isEqualTo(shareDreamPage.getTotalElements());
    }

    @Test
    void 내가_공유한_꿈_목록() {
        //given
        int page= 0;
        int size = 1;

        User user = UserBuilder.build();
        ShareDream shareDream = ShareDreamBuilder.build(user);
        Page<ShareDream> shareDreamPage = new PageImpl(List.of(shareDream));

        given(securityContextService.getPrincipal()).willReturn(new AuthenticationDetails(user));
        given(shareDreamRepository.findByUser(any(), any())).willReturn(shareDreamPage);

        //when
        ShareDreamGroupResponse response = dreamFindService.queryMyShareDreams(page, size);

        //then
        assertThat(response.getShareDreams().size()).isEqualTo(size);
        assertThat(response.getTotalPage()).isEqualTo(shareDreamPage.getTotalPages());
        assertThat(response.getTotalSize()).isEqualTo(shareDreamPage.getTotalElements());
    }

    @Test
    void 날짜별_꿈_목록() {
        //given
        User user = UserBuilder.build();
        ShareDream shareDream = ShareDreamBuilder.build(user);

        int year = shareDream.getSleepDateTime().getYear();
        int month = shareDream.getSleepDateTime().getMonthValue();

        given(securityContextService.getPrincipal()).willReturn(new AuthenticationDetails(user));
        given(shareDreamRepository.findByUserAndSleepDateTimeBetween(any(User.class), any(LocalDateTime.class), any(LocalDateTime.class))).willReturn(List.of(shareDream));

        ShareDreamTimeTableResponse response = dreamFindService.queryMyShareDreamTimeTable(year, month);

        assertThat(response.getTimetables().get(shareDream.getSleepDateTime().toLocalDate()).size()).isEqualTo(1);
    }
}