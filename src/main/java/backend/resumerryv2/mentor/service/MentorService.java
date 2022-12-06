/* Licensed under InfoCat */
package backend.resumerryv2.mentor.service;

import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.domain.repository.MentorCustomRepository;
import backend.resumerryv2.mentor.domain.repository.MentorRepository;
import backend.resumerryv2.mentor.web.dto.FieldOfMentorList;
import backend.resumerryv2.user.domain.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MentorService {
  private final MentorCustomRepository mentorCustomRepository;

  private final MentorRepository mentorRepository;

  public Page<MentorContent> getMentorList(
      List<Integer> field, List<Integer> category, String sorted, String title, Pageable pageable) {
    FieldOfMentorList fieldOfMentorList = new FieldOfMentorList(field, category, sorted, title);
    return mentorCustomRepository.searchAll(fieldOfMentorList, pageable);
  }

  public boolean getUserIsMentor(User user) {
    if (mentorRepository.findMentorByUser(user).isPresent()) {
      return true;
    }
    return false;
  }
}
