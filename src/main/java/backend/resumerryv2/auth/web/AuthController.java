package backend.resumerryv2.auth.web;

import backend.resumerryv2.auth.domain.dto.*;
import backend.resumerryv2.auth.service.AuthService;
import backend.resumerryv2.auth.service.EmailService;
import backend.resumerryv2.global.dto.GlobalResponse;
import backend.resumerryv2.exception.validation.ValidationSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/validation")
    public ResponseEntity<GlobalResponse> checkSignUpValidation(
            @Validated(ValidationSequence.class) @RequestBody ValidationRequest validationRequest
            ){
        return ResponseEntity.ok(GlobalResponse.ofSuccess());
    }

    @PostMapping("/email")
    public ResponseEntity<GlobalResponse> sendValidationCodeEmail(
            @RequestBody EmailRequest email
    ){
        // 이메일 전송
        Integer validationCode = emailService.setValidationCode();
        SimpleMailMessage simpleMailMessage = emailService.setValidationCodeEmailForm(email.getEmail(), validationCode);
        emailService.sendEmail(email.getEmail(), simpleMailMessage);
        return ResponseEntity.ok(GlobalResponse.ofSuccess());
    }


}
