/* Licensed under InfoCat */
package backend.resumerryv2.mentor.web;

import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.service.MentorService;
import backend.resumerryv2.mentor.web.dto.MentorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MentorController {
  private final MentorService mentorService;

  @GetMapping("/mentoring/posts")
  public ResponseEntity<Page<MentorContent>> getMentors(
      @RequestParam(required = false) List<Integer> field,
      @RequestParam(required = false) List<Integer> category,
      @RequestParam(required = false) String sorted,
      @RequestParam(required = false) String title,
      Pageable pageable) {
    Page<MentorContent> mentors =
        mentorService.getMentorList(field, category, sorted, title, pageable);
    return ResponseEntity.ok(mentors);
  }

  @PostMapping("/mentor/{user_id}")
  public ResponseEntity<?> createMentor(
          @PathVariable("user_id") Long userId,
          @RequestBody MentorRequest mentorRequest
          ){
    return ResponseEntity.ok().build();
  }
}
