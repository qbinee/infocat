package backend.resumerryv2.auth.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEmailRequest {
    @Email(message = "이메일 형식이 올바르지 않습니다.") private String email;
}
