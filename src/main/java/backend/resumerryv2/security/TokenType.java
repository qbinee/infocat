/* Licensed under InfoCat */
package backend.resumerryv2.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum TokenType {
    ACCESS_TOKEN("AccessToken", 3600),
    VALIDATION_TOKEN("ValidationToken", 600),
    ;
    private String key;
    private Integer value;
}
