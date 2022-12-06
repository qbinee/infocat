/* Licensed under InfoCat */
package backend.resumerryv2.user.service;

import backend.resumerryv2.auth.domain.dto.TokenDTO;
import backend.resumerryv2.exception.CustomException;
import backend.resumerryv2.exception.ErrorType;
import backend.resumerryv2.security.JwtProvider;
import backend.resumerryv2.security.TokenType;
import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.user.domain.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final JwtProvider jwtProvider;

  public TokenDTO.Response login(String email, String password) {
    checkEmailAndPassword(email, password);
    String accessToken = jwtProvider.generateToken(email, TokenType.ACCESS_TOKEN);
    return TokenDTO.Response.builder().accessToken(accessToken).build();
  }

  private User getUser(String email) {
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isEmpty()) {
      throw new CustomException(HttpStatus.NOT_FOUND, ErrorType.INVALID_USER);
    }
    return user.get();
  }

  private Boolean checkEmailAndPassword(String email, String password) {
    if (!getUser(email).getPassword().equals(password)) {
      throw new CustomException(HttpStatus.UNAUTHORIZED, ErrorType.UNAUTHORIZED);
    }
    return true;
  }
}
