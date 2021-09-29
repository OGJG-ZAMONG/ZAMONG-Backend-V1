package app.jg.og.zamong.exception;

import app.jg.og.zamong.exception.business.BusinessException;
import app.jg.og.zamong.exception.externalinfra.ExternalInfraException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorDescription = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_PARAMETER, errorDescription);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String errorDescription = e.getMessage();
        ErrorResponse response = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED, errorDescription);

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        String errorDescription = e.getMessage();
        ErrorResponse response = ErrorResponse.of(errorCode, errorDescription);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(ExternalInfraException.class)
    public ResponseEntity<ErrorResponse> handleExternalInfraException(ExternalInfraException e) {
        ErrorCode errorCode = e.getErrorCode();
        String errorDescription = e.getMessage();
        ErrorResponse response = ErrorResponse.of(errorCode, errorDescription);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        String errorDescription = "서버 오류";
        ErrorResponse response = ErrorResponse.of(errorCode, errorDescription);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }
}
