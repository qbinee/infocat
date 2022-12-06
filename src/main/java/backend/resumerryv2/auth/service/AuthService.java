package backend.resumerryv2.auth.service;

import backend.resumerryv2.auth.domain.EmailValidation;
import backend.resumerryv2.auth.web.dto.LoginResponse;
import backend.resumerryv2.auth.web.dto.SignUpRequest;
import backend.resumerryv2.exception.CustomException;
import backend.resumerryv2.exception.ErrorType;
import backend.resumerryv2.mentor.service.MentorService;
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

    private final MentorService mentorService;

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public LoginResponse login(String email, String password){
        User user = checkEmailAndPassword(email, password);
        return new LoginResponse(mentorService.getUserIsMentor(user));
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
        if(userService.getUser(email) instanceof User)
            throw new CustomException(HttpStatus.FORBIDDEN, ErrorType.DUPLICATED_USER);
    }

    private User checkEmailAndPassword(String email, String password){
        User user = userService.getUser(email);
        if(!user.getPassword().equals(password)){
            throw new CustomException(HttpStatus.UNAUTHORIZED, ErrorType.UNAUTHORIZED);
        }
        return user;
    }
}
