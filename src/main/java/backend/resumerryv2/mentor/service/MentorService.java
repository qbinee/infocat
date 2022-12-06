/* Licensed under InfoCat */
package backend.resumerryv2.mentor.service;

import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.domain.repository.MentorCustomRepository;
import backend.resumerryv2.mentor.web.dto.FieldOfMentorList;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MentorService {
  private final MentorCustomRepository mentorCustomRepository;

  public Page<MentorContent> getMentorList(
      List<Integer> field, List<Integer> category, String sorted, String title, Pageable pageable) {
    FieldOfMentorList fieldOfMentorList = new FieldOfMentorList(field, category, sorted, title);
    return mentorCustomRepository.searchAll(fieldOfMentorList, pageable);
  }
}
