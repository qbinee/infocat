package backend.resumerryv2.user.domain.repository;

import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.user.web.dto.ClassSessionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCustomRepository {
    Page<ClassSessionResponse> getClassSession(User user, Pageable pageable);
}
