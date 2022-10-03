package backend.resumerryv2.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentNotValidExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        MethodArgumentNotValidExceptionResponse methodArgumentNotValidExceptionResponse = MethodArgumentNotValidExceptionResponse.of(ErrorType.REQUEST_VALIDATION_ERROR);
        for(FieldError fieldError: e.getFieldErrors()) {
            methodArgumentNotValidExceptionResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(methodArgumentNotValidExceptionResponse);
    }
}
