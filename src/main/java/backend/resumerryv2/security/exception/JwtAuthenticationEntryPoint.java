/* Licensed under InfoCat */
package backend.resumerryv2.security.exception;

import backend.resumerryv2.exception.CustomException;
import backend.resumerryv2.exception.ErrorType;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * @param request that resulted in an <code>AuthenticationException</code>
   * @param response so that the user agent can begin authentication
   * @param e that caused the invocation
   * @throws IOException
   */
  @Override
  public void commence(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
      throws IOException {

    log.error("Responding with unauthorized error. Message - {}", e.getMessage());
    throw new CustomException(HttpStatus.UNAUTHORIZED, ErrorType.UNAUTHORIZED);
  }
}
