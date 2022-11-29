package backend.resumerryv2.mentor.web;

import backend.resumerryv2.mentor.domain.dto.FieldOfMentorList;
import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.service.MentorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentoring")
public class MentorController {
    private final MentorService mentorService;
    @GetMapping("/posts")
    public ResponseEntity<?> getMentors(
                                        @RequestParam(required = false) String field,
                                        @RequestParam(required = false) String category,
                                        @RequestParam(required = false) String sorted,
                                        Pageable pageable
    ){
        Page<MentorContent> mentors = mentorService.getMentorList(field, category, sorted, pageable);
        return ResponseEntity.ok().body(mentors);
    }
}
