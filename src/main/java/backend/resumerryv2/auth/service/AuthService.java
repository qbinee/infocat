package backend.resumerryv2.auth.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public String getValidationCode(String email) {
        return "test";
    }
}
