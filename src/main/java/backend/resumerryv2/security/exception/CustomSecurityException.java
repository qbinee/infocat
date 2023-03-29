/* Licensed under InfoCat */
package backend.resumerryv2.security.exception;

import backend.resumerryv2.exception.ErrorResponse;
import backend.resumerryv2.exception.ErrorType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomSecurityException extends Exception {
    private static final ObjectMapper JSON_CONVERTER = new ObjectMapper();

    public CustomSecurityException(String message) {
        super(message);
    }

    public CustomSecurityException(ErrorType errorType) {
        this(generateExceptionMessage(errorType));
    }

    private static String generateExceptionMessage(ErrorType errorType) {
        try {
            return JSON_CONVERTER.writeValueAsString(
                    new ErrorResponse(errorType.getErrorCode(), errorType.getMessage()));
        } catch (JsonProcessingException e) {
            return "JWT 인증 오류";
        }
    }
}
