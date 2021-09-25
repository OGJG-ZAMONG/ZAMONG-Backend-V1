package app.jg.og.zamong.dto.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
@Builder
public class EmailAuthenticationRequest {

    @Email
    private final String address;
}
