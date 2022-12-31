/* Licensed under InfoCat */
package backend.resumerryv2.auth.service.template;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EmailForm {
  SIGNUP_AUTH_REQUEST("[Infocat] 인증번호는 %s 입니다."),
  VALIDATION_CODE_FORM(
      "안녕하세요 \n"
          + "\n"
          + "요청하신 이메일 인증번호를 안내 드립니다.\n"
          + "\n"
          + "아래 번호를 입력하여 인증 절차를 완료해 주세요.\n"
          + "\n"
          + "본 인증번호는 10분 후에 만료됩니다.\n");

  private final String message;

  public static String getValidationCodeEmailForm(Integer validationCode) {
    return String.format(VALIDATION_CODE_FORM + SIGNUP_AUTH_REQUEST.message, validationCode);
  }
}
