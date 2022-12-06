package backend.resumerryv2.auth.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class TokenDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Request {
        private String email;
        private String password;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Response {
        private String validationToken;
    }


}