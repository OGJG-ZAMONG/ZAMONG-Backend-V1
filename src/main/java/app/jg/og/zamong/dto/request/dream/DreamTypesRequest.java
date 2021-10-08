package app.jg.og.zamong.dto.request.dream;

import app.jg.og.zamong.entity.dream.enums.DreamType;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DreamTypesRequest {

    @NotNull(message = "꿈의 유형을 입력해주세요")
    private List<DreamType> dreamTypes;
}
