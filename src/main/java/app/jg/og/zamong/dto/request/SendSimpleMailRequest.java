package app.jg.og.zamong.dto.request;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendSimpleMailRequest {

    private String address;

    private String content;

    private String title;
}
