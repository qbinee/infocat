/* Licensed under InfoCat */
package backend.resumerryv2.user.service;

import backend.resumerryv2.exception.CustomException;
import backend.resumerryv2.exception.ErrorType;
import backend.resumerryv2.security.CustomUserDetails;
import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.user.domain.repository.UserCustomRepository;
import backend.resumerryv2.user.domain.repository.UserRepository;
import backend.resumerryv2.user.web.dto.ClassSessionResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;

    public Optional<User> getUser(String email) {
        return userRepository.findByEmailAndIsDeleteFalse(email);
    }

    public Page<ClassSessionResponse> getUserClassSession(
            CustomUserDetails userDetails, Pageable pageable) {
        User user = findUserByEmail(userDetails.getEmail());
        return userCustomRepository.getClassSession(user, pageable);
    }

    private User findUserByEmail(String email) {
        return userRepository
                .findByEmailAndIsDeleteFalse(email)
                .orElseThrow(
                        () -> new CustomException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_USER));
    }
}
