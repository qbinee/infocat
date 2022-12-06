/* Licensed under InfoCat */
package backend.resumerryv2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
  private String errorCode;
  private String message;

  public static ErrorResponse of(ErrorType errorType) {
    return new ErrorResponse(errorType.getErrorCode(), errorType.getMessage());
  }
}
