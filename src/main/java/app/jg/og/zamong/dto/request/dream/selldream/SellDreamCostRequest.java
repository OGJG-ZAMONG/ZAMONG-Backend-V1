package app.jg.og.zamong.dto.request.dream.selldream;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SellDreamCostRequest {

    @NotNull(message = "가격을 반드시 입력해야 합니다")
    @Range
    private Integer cost;
}
