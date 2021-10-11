package app.jg.og.zamong.entity.dream.attachment;

import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.attachment.converter.DreamTagConverter;
import app.jg.og.zamong.entity.dream.enums.DreamTag;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "attachment_image")
public class AttachmentImage {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(columnDefinition = "tinyint")
    private Integer order;

    private String path;

    private String host;

    @Convert(converter = DreamTagConverter.class)
    @Column(columnDefinition = "char(4)")
    private DreamTag tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dream_uuid")
    private Dream dream;

    public String getUrl() {
        return host + path;
    }
}
