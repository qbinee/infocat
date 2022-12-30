package backend.resumerryv2.auth.web;

import backend.resumerryv2.auth.web.dto.*;
import backend.resumerryv2.auth.service.AuthService;
import backend.resumerryv2.exception.validation.ValidationSequence;
import backend.resumerryv2.global.domain.dto.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<GlobalResponse> signUp(
            @RequestHeader("validation-token") String validationToken ,
            @Validated(ValidationSequence.class) @RequestBody SignUpRequest signUpRequest
            ){
        authService.signUp(validationToken, signUpRequest);
        return ResponseEntity.ok(GlobalResponse.ofSuccess());
    }

    @PostMapping("/validation")
    public ResponseEntity<GlobalResponse> checkValidation(
            @Validated(ValidationSequence.class) @RequestBody SignUpRequest signUpRequest
    ){
        authService.checkDuplicatedUser(signUpRequest.getEmail());
        return ResponseEntity.ok(GlobalResponse.ofSuccess());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Validated(ValidationSequence.class) @RequestBody TokenDTO.Request request,
            HttpServletResponse response
    ){
        LoginResponse loginResponse = authService.login(request.getEmail(), request.getPassword());
        response.addCookie(authService.getAccessTokenCookie(request.getEmail()));
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/email")
    public ResponseEntity<CompanyEmailResponse> certifiedCompanyEmail(
            @Validated(ValidationSequence.class) @RequestBody CompanyEmailRequest email
            ){
        return ResponseEntity.ok(authService.certificatedEmail(email));
    }

    @PostMapping("/logout")
    public ResponseEntity<GlobalResponse> logout(
            HttpServletResponse response
    ){
        Cookie myCookie = new Cookie("AccessToken", null);
        myCookie.setMaxAge(0); // 쿠키의 expiration 타임을 0으로 하여 없앤다.
        myCookie.setPath("/");
        response.addCookie(myCookie);
        return ResponseEntity.ok(GlobalResponse.ofSuccess());
    }

}
