package app.jg.og.zamong.entity.dream.interpretationdream;

import app.jg.og.zamong.entity.dream.Dream;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class InterpretationDream extends Dream {

    @Setter
    private Integer lucyCount;

    @Setter
    private Boolean isInterpretation;
}
