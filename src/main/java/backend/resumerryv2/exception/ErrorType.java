package backend.resumerryv2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {

    REQUEST_VALIDATION_ERROR("Invalid Value", "유효하지않은 Request 형식입니다."),
    ;

    private String errorCode;
    private String message;

}
