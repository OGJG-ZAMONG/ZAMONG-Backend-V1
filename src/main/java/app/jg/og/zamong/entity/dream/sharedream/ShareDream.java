package app.jg.og.zamong.entity.dream.sharedream;

import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.enums.DreamQuality;
import app.jg.og.zamong.entity.dream.sharedream.converter.DreamQualityConverter;
import app.jg.og.zamong.entity.dream.sharedream.lucypoint.ShareDreamLucyPoint;
import app.jg.og.zamong.exception.business.AlreadySharedException;
import app.jg.og.zamong.exception.business.NotSharedException;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
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

    private Integer lucyCount;

    @Column(name = "share_datetime", columnDefinition = "timestamp")
    private LocalDateTime shareDateTime;

    @OneToMany(mappedBy = "shareDream")
    private List<ShareDreamLucyPoint> shareDreamLucyPoints;

    public void doShare() {
        if(isShared) {
            throw new AlreadySharedException("이미 공유한 꿈입니다");
        }
        isShared = true;
        shareDateTime = LocalDateTime.now();
    }

    public void cancelShare() {
        if(!isShared) {
            throw new NotSharedException("공유되지 않은 꿈입니다");
        }
        isShared = false;
        shareDateTime = null;
    }

    public void addLucy() {
        lucyCount += 1;
    }

    public void cancelLucy() {
        lucyCount -= 1;
    }
}
