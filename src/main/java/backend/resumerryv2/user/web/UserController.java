package backend.resumerryv2.user.web;



import backend.resumerryv2.auth.domain.dto.TokenDTO;
import backend.resumerryv2.exception.validation.ValidationSequence;
import backend.resumerryv2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;



    @PostMapping("/login")
    public ResponseEntity<TokenDTO.Response> login(
            @Validated(ValidationSequence.class) @RequestBody TokenDTO.Request request
            ){
        return ResponseEntity.ok().body(userService.login(request.getEmail(), request.getPassword()));
    }

    @PostMapping("/sample")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok().build();
    }
}
