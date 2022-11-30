/* Licensed under InfoCat */
package backend.resumerryv2.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
  private final HttpStatus httpStatus;
  private final ErrorResponse body;

  public CustomException(HttpStatus httpStatus, ErrorType errorType) {
    super(errorType.getMessage());
    this.httpStatus = httpStatus;
    this.body = new ErrorResponse(errorType.getErrorCode(), errorType.getMessage());
  }
}
