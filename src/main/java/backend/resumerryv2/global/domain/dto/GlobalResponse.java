/* Licensed under InfoCat */
package backend.resumerryv2.global.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GlobalResponse {

    private static final String STATUS_SUCCESS = "true";
    private static final String STATUS_FAILURE = "false";
    private String result;

    public static GlobalResponse ofSuccess() {
        return new GlobalResponse(STATUS_SUCCESS);
    }
}
