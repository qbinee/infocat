package backend.resumerryv2.auth.web;

import backend.resumerryv2.auth.domain.EmailValidation;
import backend.resumerryv2.auth.domain.dto.ValidationCodeRequest;
import backend.resumerryv2.auth.service.EmailService;
import backend.resumerryv2.global.dto.GlobalResponse;
import backend.resumerryv2.security.JwtProvider;
import backend.resumerryv2.security.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;


@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Validated
public class EmailController {

    private final EmailService emailService;

    private final JwtProvider jwtProvider;

    private final Integer expireTime = 600;

    @GetMapping()
    public ResponseEntity<GlobalResponse> sendEmail(
             @RequestParam @Email String email
    ){
        emailService.getValidationEmail(email);
        return ResponseEntity.ok(GlobalResponse.ofSuccess());
    }

    @PostMapping()
    public  ResponseEntity<GlobalResponse> checkValidationCode(
            @RequestBody ValidationCodeRequest validationCodeRequest
            ){
        EmailValidation e = emailService.checkValidationCode(validationCodeRequest.getEmail(), validationCodeRequest.getValidationCode());
        HttpHeaders headers = new HttpHeaders();
        headers.add("validation-token", jwtProvider.generateToken(String.valueOf(validationCodeRequest.getValidationCode()), TokenType.VALIDATION_TOKEN));
        return ResponseEntity.ok().headers(headers).body(GlobalResponse.ofSuccess());
    }


}
