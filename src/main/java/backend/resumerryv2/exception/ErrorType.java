package backend.resumerryv2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {

    REQUEST_VALIDATION_ERROR(400,"VALIDATION-ERR-01", "유효하지않은 Request 형식입니다."),

    NOT_VALID_CODE_ERROR(404, "VALIDATION-ERR-02", "유효번호가 일치하지 않습니다.")
    ;
    private Integer status;
    private String errorCode;
    private String message;

}
