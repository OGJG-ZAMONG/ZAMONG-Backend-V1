package app.jg.og.zamong.entity.dream.selldream.buyrequest;

import app.jg.og.zamong.entity.dream.selldream.SellDream;
import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.exception.business.ForbiddenStatusSellDreamException;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SellDreamBuyRequest {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(columnDefinition = "tinyint(1)")
    private Boolean isAccept;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "sell_dream_uuid", nullable = false)
    private SellDream sellDream;

    @Column(name = "datetime", columnDefinition = "timestamp")
    private LocalDateTime dateTime;

    public void acceptBuyRequest() {
        if (isAccept) {
            throw new ForbiddenStatusSellDreamException("이미 인증 수락된 요청");
        }
        isAccept = true;
    }
}
