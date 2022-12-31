/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain.repository.custom;

import backend.resumerryv2.mentor.domain.Mentor;
import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.domain.dto.MentoringContent;
import backend.resumerryv2.mentor.web.dto.FieldOfMentorList;
import backend.resumerryv2.user.web.dto.ClassSessionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MentorCustomRepository {
  Page<MentorContent> searchAll(FieldOfMentorList f, Pageable p);
  MentoringContent getMentoringClassInfo(Long mentoringClassId);

  List<String> getBookingDaysByMentoringClassId(Long mentoringClassId);
  Page<ClassSessionResponse> getClassSession(Mentor mentor, Pageable pageable);

}
