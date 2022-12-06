package backend.resumerryv2.auth.service;

import backend.resumerryv2.auth.domain.EmailValidation;
import backend.resumerryv2.auth.web.dto.LoginResponse;
import backend.resumerryv2.auth.web.dto.SignUpRequest;
import backend.resumerryv2.exception.CustomException;
import backend.resumerryv2.exception.ErrorType;
import backend.resumerryv2.security.JwtProvider;
import backend.resumerryv2.security.TokenType;
import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.user.domain.repository.UserRepository;
import backend.resumerryv2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public LoginResponse login(String email, String password){
        userService.checkEmailAndPassword(email, password);
        return new LoginResponse(true);
    }

    public Cookie getAccessTokenCookie(String email){
        String accessToken = jwtProvider.generateToken(email, TokenType.ACCESS_TOKEN);
        Cookie cookie = new Cookie("AccessToken", accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    public void signUp(String validationToken, SignUpRequest signUpRequest){
        jwtProvider.verify(validationToken);
        EmailValidation e = emailService.checkValidationCode(signUpRequest.getEmail(), Integer.valueOf(jwtProvider.decordToken(validationToken)));
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .nickname(signUpRequest.getNickname())
                .password(signUpRequest.getPassword())
                .build();
        userRepository.save(user);
        emailService.deleteValidationCode(e);
    }

    public void checkDuplicatedUser(String email){
        if(userRepository.findByEmail(email).isPresent())
            throw new CustomException(HttpStatus.FORBIDDEN, ErrorType.DUPLICATED_USER);
    }
}
