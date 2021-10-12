package app.jg.og.zamong.entity.dream;

import app.jg.og.zamong.entity.dream.attachment.AttachmentImage;
import app.jg.og.zamong.entity.dream.dreamtype.DreamType;
import app.jg.og.zamong.entity.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate()
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid", nullable = false)
    private User user;

    @OneToMany(mappedBy = "dream")
    private List<DreamType> dreamTypes;

    @OneToMany(mappedBy = "dream")
    private List<AttachmentImage> attachmentImages;

    public String getDefaultImage() {
        return attachmentImages.get(0).getUrl();
    }
}
