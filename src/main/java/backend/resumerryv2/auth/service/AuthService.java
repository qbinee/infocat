/* Licensed under InfoCat */
package backend.resumerryv2.auth.service;

import backend.resumerryv2.auth.domain.dto.SignUpRequest;
import backend.resumerryv2.exception.CustomException;
import backend.resumerryv2.exception.ErrorType;
import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;

  public void signUp(SignUpRequest signUpRequest) {
    User user =
        User.builder()
            .email(signUpRequest.getEmail())
            .nickname(signUpRequest.getNickname())
            .password(signUpRequest.getPassword())
            .build();
    userRepository.save(user);
  }

  public void checkDuplicatedUser(String email) {
    if (!userRepository.findByEmail(email).isPresent())
      throw new CustomException(HttpStatus.FORBIDDEN, ErrorType.DUPLICATED_USER);
  }
}
