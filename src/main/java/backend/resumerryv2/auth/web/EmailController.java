/* Licensed under InfoCat */
package backend.resumerryv2.auth.web;

import backend.resumerryv2.auth.service.EmailService;
import backend.resumerryv2.auth.web.dto.TokenDTO;
import backend.resumerryv2.auth.web.dto.ValidationCodeRequest;
import backend.resumerryv2.global.domain.dto.GlobalResponse;
import backend.resumerryv2.security.JwtProvider;
import backend.resumerryv2.security.TokenType;
import javax.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
@Validated
public class EmailController {

    private final EmailService emailService;

    private final JwtProvider jwtProvider;

    @GetMapping
    public ResponseEntity<GlobalResponse> sendEmail(@RequestParam @Email String email) {
        emailService.getValidationEmail(email);
        return ResponseEntity.ok(GlobalResponse.ofSuccess());
    }

    @PostMapping
    public ResponseEntity<TokenDTO.Response> checkValidationCode(
            @RequestBody ValidationCodeRequest validationCodeRequest) {
        emailService.checkValidationCode(
                validationCodeRequest.getEmail(), validationCodeRequest.getValidationCode());
        String validationToken =
                jwtProvider.generateToken(
                        String.valueOf(validationCodeRequest.getValidationCode()),
                        TokenType.VALIDATION_TOKEN);
        return ResponseEntity.ok().body(new TokenDTO.Response(validationToken));
    }
}
