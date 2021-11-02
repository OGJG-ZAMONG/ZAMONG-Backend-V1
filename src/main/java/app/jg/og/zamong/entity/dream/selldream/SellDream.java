package app.jg.og.zamong.entity.dream.selldream;

import app.jg.og.zamong.entity.dream.Dream;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SellDream extends Dream {

    @Setter
    private Integer cost;
}
