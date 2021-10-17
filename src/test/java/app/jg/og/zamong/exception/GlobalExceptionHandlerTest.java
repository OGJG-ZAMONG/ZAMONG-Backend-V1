package app.jg.og.zamong.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleHttpRequestMethodNotSupportedExceptionTest() {
        HttpRequestMethodNotSupportedException exception = new HttpRequestMethodNotSupportedException(HttpMethod.GET.name());
        ResponseEntity<ErrorResponse> responseEntity = handler.handleHttpRequestMethodNotSupportedException(exception);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    void handleConstraintViolationExceptionTest() {
        ConstraintViolationException exception = new ConstraintViolationException(null);
        ResponseEntity<ErrorResponse> responseEntity = handler.handleConstraintViolationException(exception);

        assertThat(responseEntity.getBody().getDescription()).isEqualTo(exception.getMessage());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleIllegalStateExceptionTest() {
        IllegalStateException exception = new IllegalStateException();
        ResponseEntity<ErrorResponse> responseEntity = handler.handleIllegalStateException(exception);

        assertThat(responseEntity.getBody().getDescription()).isEqualTo(exception.getMessage());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handleExceptionTest() {
        RuntimeException exception = new RuntimeException();
        ResponseEntity<ErrorResponse> responseEntity = handler.handleException(exception);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
