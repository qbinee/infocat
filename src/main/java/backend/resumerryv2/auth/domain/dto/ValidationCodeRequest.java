package backend.resumerryv2.auth.domain.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;


@Data
@RequiredArgsConstructor
@Validated
public class ValidationCodeRequest {
    @Email private String email;
    @DecimalMin(value = "99999") @DecimalMax(value = "999999") private Integer validationCode;
}
