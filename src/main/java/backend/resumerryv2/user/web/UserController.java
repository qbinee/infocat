package backend.resumerryv2.user.web;


import backend.resumerryv2.security.CustomUserDetails;
import backend.resumerryv2.user.service.UserService;
import backend.resumerryv2.user.web.dto.ClassSessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @GetMapping("/my_page/class_session")
    public ResponseEntity<Page<ClassSessionResponse>> getClassSession(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Pageable pageable
            ){
        Page<ClassSessionResponse> classSessionResponses = userService.getClassSession(userDetails, pageable);
        return ResponseEntity.ok(classSessionResponses);
    }
}
