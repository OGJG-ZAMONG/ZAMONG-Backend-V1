package app.jg.og.zamong.entity.dream.selldream.chatting.room;

import app.jg.og.zamong.entity.dream.selldream.SellDream;
import app.jg.og.zamong.entity.dream.selldream.chatting.chat.SellDreamChatting;
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
public class SellDreamChattingRoom {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "last_chat_uuid")
    private SellDreamChatting lastChat;

    @ManyToOne
    @JoinColumn(name = "seller_uuid", nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name = "customer_uuid", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "sell_dream_uuid", nullable = false)
    private SellDream sellDream;
}
