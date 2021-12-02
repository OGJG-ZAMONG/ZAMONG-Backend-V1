package app.jg.og.zamong.dto.request.dream.interpretationdream;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelectInterpretationDreamRequest {

    private UUID dreamUuid;

    private UUID commentUuid;
}
