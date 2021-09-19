package app.jg.og.zamong.dto.request;

import app.jg.og.zamong.entity.user.User;
import app.jg.og.zamong.util.UserBuilder;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SignUpUserRequestTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    void 인증코드_여섯자리아니면_실패() {
        //given
        String authorizationCode = "1234567";
        SignUpUserRequest request = getSignUpRequestWithAuthorizationCode(authorizationCode);

        //when
        Set<ConstraintViolation<SignUpUserRequest>> constraintViolations = validator.validate(request);

        //then
        assertThat(authorizationCode.length()).isEqualTo(7);
        assertThat(constraintViolations)
                .extracting(ConstraintViolation::getInvalidValue)
                .contains(authorizationCode);
    }

    @Test
    void 인증코드_여섯자리면_성공() {
        //given
        String authorizationCode = "123456";
        SignUpUserRequest request = getSignUpRequestWithAuthorizationCode(authorizationCode);

        //when
        Set<ConstraintViolation<SignUpUserRequest>> constraintViolations = validator.validate(request);

        //then
        assertThat(constraintViolations)
                .extracting(ConstraintViolation::getInvalidValue)
                .isNullOrEmpty();
    }

    private SignUpUserRequest getSignUpRequestWithAuthorizationCode(String authorizationCode) {
        User user = UserBuilder.build();
        return SignUpUserRequest.builder()
                .name(user.getName())
                .email(user.getEmail())
                .authenticationCode(authorizationCode)
                .id(user.getId())
                .password(user.getPassword())
                .build();
    }
}
