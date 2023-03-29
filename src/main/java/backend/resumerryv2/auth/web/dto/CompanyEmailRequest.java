/* Licensed under InfoCat */
package backend.resumerryv2.auth.web.dto;

import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEmailRequest {
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
}
