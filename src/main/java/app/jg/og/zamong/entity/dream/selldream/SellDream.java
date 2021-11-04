package app.jg.og.zamong.entity.dream.selldream;

import app.jg.og.zamong.entity.dream.Dream;
import app.jg.og.zamong.entity.dream.enums.SalesStatus;
import app.jg.og.zamong.entity.dream.selldream.converter.SalesStatusConverter;
import app.jg.og.zamong.exception.business.CantCancelSellDreamException;
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

    public void candleSale() {
        checkPendingStatus();
        status = SalesStatus.CANCEL;
    }

    private void checkPendingStatus() {
        if(status != SalesStatus.PENDING) {
            throw new CantCancelSellDreamException("취소할 수 없습니다");
        }
    }
}
