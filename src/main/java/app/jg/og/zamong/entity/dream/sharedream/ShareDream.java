package app.jg.og.zamong.entity.dream.sharedream;

import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.DreamQuality;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.parameters.P;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "share_dream")
public class ShareDream extends Dream {

    @Column(length = 4, columnDefinition = "char(4)")
    private String quality;

    @Column(length = 4, columnDefinition = "char(4)")
    private String type;

    @Column(columnDefinition = "tinyint(1)")
    private Boolean isShared;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sleepDateTime;

    @Column(columnDefinition = "tinyint")
    private Integer sleepTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date shareDateTime;
}
