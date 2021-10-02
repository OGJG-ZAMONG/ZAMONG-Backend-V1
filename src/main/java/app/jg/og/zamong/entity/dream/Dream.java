package app.jg.og.zamong.entity.dream;

import app.jg.og.zamong.entity.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Dream {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Setter
    @Column(length = 100)
    private String title;

    @Setter
    @Column(columnDefinition = "text")
    private String content;

    @Setter
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;
}
