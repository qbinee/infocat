package backend.resumerryv2.user.service;

import backend.resumerryv2.exception.CustomException;
import backend.resumerryv2.exception.ErrorType;
import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private User getUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()){
            throw new CustomException(HttpStatus.NOT_FOUND, ErrorType.INVALID_USER);
        }
        return user.get();
    }

    public Boolean checkEmailAndPassword(String email, String password){
        if(!getUser(email).getPassword().equals(password)){
            throw new CustomException(HttpStatus.UNAUTHORIZED, ErrorType.UNAUTHORIZED);
        }
        return true;
    }

}
