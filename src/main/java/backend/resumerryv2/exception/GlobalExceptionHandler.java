package backend.resumerryv2.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getBody());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentNotValidExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        MethodArgumentNotValidExceptionResponse methodArgumentNotValidExceptionResponse = MethodArgumentNotValidExceptionResponse.of(ErrorType.REQUEST_VALIDATION_ERROR);
        for(FieldError fieldError: e.getFieldErrors()) {
            methodArgumentNotValidExceptionResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(methodArgumentNotValidExceptionResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MethodArgumentNotValidExceptionResponse> handleConstraintViolationException(ConstraintViolationException e){
        MethodArgumentNotValidExceptionResponse methodArgumentNotValidExceptionResponse = MethodArgumentNotValidExceptionResponse.of(ErrorType.REQUEST_VALIDATION_ERROR);
        for(ConstraintViolation<?> fieldError: e.getConstraintViolations()) {
            methodArgumentNotValidExceptionResponse.addValidation(fieldError.getMessageTemplate(), fieldError.getMessage());
        }
        return ResponseEntity.badRequest().body(methodArgumentNotValidExceptionResponse);
    }
}
