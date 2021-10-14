package app.jg.og.zamong.entity.follow;

import app.jg.og.zamong.entity.user.User;
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
public class Follow {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    private User following;

    @ManyToOne
    @JoinColumn(name = "follower_uuid", nullable = false)
    private User follower;

    @Column(name = "follow_datetime", columnDefinition = "timestamp")
    private LocalDateTime followDateTime;
}
