/* Licensed under InfoCat */
package backend.resumerryv2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {
  REQUEST_VALIDATION_ERROR("VALIDATION-ERR-01", "유효하지않은 Request 형식입니다."),

  NOT_VALID_CODE_ERROR("VALIDATION-ERR-02", "인증번호가 일치하지 않습니다."),
  EXPIRED_VALID_CODE("VALIDATION-ERR-03", "인증번호가 만료되었습니다"),

  DUPLICATED_USER("USER-ERR-01", "이미 존재하는 유저입니다."),
  INVALID_USER("USER-ERR-02", "존재하지 않은 유저입니다."),
  UNAUTHORIZED("AUTH-ERR-01", "인증에 실패하였습니다.");
  private String errorCode;
  private String message;
}
