package app.jg.og.zamong.exception;

import app.jg.og.zamong.exception.business.BusinessException;
import app.jg.og.zamong.exception.externalinfra.ExternalInfraException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        String errorDescription = e.getMessage();
        ErrorResponse response = ErrorResponse.of(errorCode, errorDescription);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
        ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        String errorDescription = e.getMessage();
        ErrorResponse response = ErrorResponse.of(errorCode, errorDescription);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(MissingServletRequestParameterException e) {
        ErrorCode errorCode = ErrorCode.INVALID_PARAMETER;
        String errorDescription = e.getMessage();
        ErrorResponse response = ErrorResponse.of(errorCode, errorDescription);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        String errorDescription = "?????? ??????";
        ErrorResponse response = ErrorResponse.of(errorCode, errorDescription);

        return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
    }
}
