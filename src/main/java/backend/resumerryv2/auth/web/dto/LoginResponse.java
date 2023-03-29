/* Licensed under InfoCat */
package backend.resumerryv2.auth.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private Boolean isMentor;
    private String nickname;
}
