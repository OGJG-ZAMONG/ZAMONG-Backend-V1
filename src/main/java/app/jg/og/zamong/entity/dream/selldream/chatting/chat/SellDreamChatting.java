package app.jg.og.zamong.entity.dream.selldream.chatting.chat;

import app.jg.og.zamong.entity.dream.selldream.chatting.room.SellDreamChattingRoom;
import app.jg.og.zamong.entity.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
public class SellDreamChatting {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    private String chat;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_uuid", nullable = false)
    private SellDreamChattingRoom room;
}
