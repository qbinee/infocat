/* Licensed under InfoCat */
package backend.resumerryv2.security;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {

    private static final String COOKIE = "Cookie";
    private static final String ACCESSTOKEN_TYPE = "AccessToken=";

    private AuthorizationExtractor() {}

    public static String extract(HttpServletRequest request) {
        String authorization = request.getHeader(COOKIE);
        if (!Objects.isNull(authorization)
                && authorization.toLowerCase().startsWith(ACCESSTOKEN_TYPE.toLowerCase())) {
            String tokenValue = authorization.substring(ACCESSTOKEN_TYPE.length()).trim();
            int commaIndex = tokenValue.indexOf(',');
            if (commaIndex > 0) {
                tokenValue = tokenValue.substring(0, commaIndex);
            }
            return tokenValue;
        }
        return null;
    }
}
