package app.jg.og.zamong.entity.dream.comment;

import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Setter
    @Column(length = 100)
    private String content;

    @Setter
    @Column(columnDefinition = "tinyint(1)")
    private Boolean isChecked;

    @Column(name = "datetime", columnDefinition = "timestamp")
    private LocalDateTime dateTime;

    private Integer depth;

    @OneToMany(mappedBy = "pComment")
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_uuid")
    private Comment pComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dream_uuid", nullable = false)
    private Dream dream;
}
