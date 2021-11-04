package app.jg.og.zamong.entity.dream.selldream;

import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.enums.SalesStatus;
import app.jg.og.zamong.entity.dream.selldream.converter.SalesStatusConverter;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SellDream extends Dream {

    @Setter
    private Integer cost;

    @Convert(converter = SalesStatusConverter.class)
    @Column(length = 4, columnDefinition = "char(4)")
    private SalesStatus status;
}
