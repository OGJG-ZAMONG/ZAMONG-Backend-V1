package app.jg.og.zamong.entity.dream.sharedream.lucypoint;

import app.jg.og.zamong.entity.dream.sharedream.ShareDream;
import app.jg.og.zamong.entity.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ShareDreamLucyPoint {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "share_dream_uuid", nullable = false)
    private ShareDream shareDream;
}
