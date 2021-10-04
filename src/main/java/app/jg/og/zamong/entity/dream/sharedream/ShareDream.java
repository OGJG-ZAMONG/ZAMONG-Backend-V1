package app.jg.og.zamong.entity.dream.sharedream;

import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamQuality;
import app.jg.og.zamong.entity.dream.sharedream.converter.DreamQualityConverter;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Date;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "share_dream")
public class ShareDream extends Dream {

    @Convert(converter = DreamQualityConverter.class)
    @Column(length = 4, columnDefinition = "char(4)")
    private DreamQuality quality;

    @Column(columnDefinition = "tinyint(1)")
    private Boolean isShared;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sleepDateTime;

    @Column(columnDefinition = "tinyint")
    private Integer sleepTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date shareDateTime;
}
