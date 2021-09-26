package app.jg.og.zamong.dto.request;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendMailRequest {

    private String address;

    private String authenticationCode;

    private String title;
}
