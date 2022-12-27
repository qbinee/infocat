package backend.resumerryv2.auth.web.dto;

import lombok.*;

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