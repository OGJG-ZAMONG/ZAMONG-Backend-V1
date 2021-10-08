package app.jg.og.zamong.entity.dream.sharedream;

import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.enums.DreamQuality;
import app.jg.og.zamong.entity.dream.sharedream.converter.DreamQualityConverter;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
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

    @Column(name = "sleep_datetime", columnDefinition = "timestamp")
    private LocalDateTime sleepDateTime;

    @Column(columnDefinition = "tinyint")
    private Integer sleepTime;

    @Column(name = "share_datetime", columnDefinition = "timestamp")
    private LocalDateTime shareDateTime;
}
