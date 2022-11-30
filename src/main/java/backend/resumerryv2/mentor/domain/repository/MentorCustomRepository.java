/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain.repository;

import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.web.dto.FieldOfMentorList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MentorCustomRepository {
  Page<MentorContent> searchAll(FieldOfMentorList f, Pageable p);
}
