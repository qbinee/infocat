package backend.resumerryv2.mentor.domain.repository;

import backend.resumerryv2.mentor.domain.dto.FieldOfMentorList;
import backend.resumerryv2.mentor.domain.dto.MentorContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MentorCustomRepository {
    Page<MentorContent> searchAll(FieldOfMentorList f, Pageable p);
}
