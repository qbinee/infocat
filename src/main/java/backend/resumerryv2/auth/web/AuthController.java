package backend.resumerryv2.auth.web;

import backend.resumerryv2.auth.domain.dto.TokenDTO;
import backend.resumerryv2.auth.service.AuthService;
import backend.resumerryv2.auth.web.dto.LoginResponse;
import backend.resumerryv2.auth.web.dto.SignUpRequest;
import backend.resumerryv2.exception.validation.ValidationSequence;
import backend.resumerryv2.global.dto.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        LoginResponse res = authService.login(request.getEmail(), request.getPassword());
        response.addCookie(authService.getAccessTokenCookie(request.getEmail()));
        return ResponseEntity.ok(res);
    }


}
