package app.jg.og.zamong.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckIdDuplicationRequest {

    @NotBlank
    @Size(min = 6, max = 16, message = "아이디는 6자 이상 16자 이하여야 합니다")
    private String id;
}
