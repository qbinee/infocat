/* Licensed under InfoCat */
package backend.resumerryv2.mentor.web;

import backend.resumerryv2.global.domain.dto.GlobalResponse;
import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.service.MentorService;
import backend.resumerryv2.mentor.web.dto.*;
import backend.resumerryv2.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

  @PostMapping("/mentor")
  public ResponseEntity<GlobalResponse> createMentor(
      @RequestBody MentorRequest mentorRequest,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    mentorService.createMentor(userDetails, mentorRequest);
    return ResponseEntity.ok(GlobalResponse.ofSuccess());
  }

  @GetMapping("/mentor")
  public ResponseEntity<MentorResponse> getMentorClass(
          @AuthenticationPrincipal CustomUserDetails userDetails
  ){
    MentorResponse response = mentorService.getMentor(userDetails);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/mentoring")
  public ResponseEntity<GlobalResponse> createMentoring(
      @RequestBody MentoringRequest mentoringRequest,
      @AuthenticationPrincipal CustomUserDetails userDetails) {
    mentorService.createMentoringClass(userDetails, mentoringRequest);
    return ResponseEntity.ok(GlobalResponse.ofSuccess());
  }

  @PostMapping("/mentoring/session")
  public ResponseEntity<GlobalResponse> createMentoringSession(
          @RequestBody MentoringSessionRequest mentoringSessionRequest,
          @AuthenticationPrincipal CustomUserDetails userDetails
          ){
    mentorService.createMentoringSession(userDetails, mentoringSessionRequest);
    return ResponseEntity.ok(GlobalResponse.ofSuccess());
  }

  @GetMapping("/mentoring/{mentoring_class_id}")
  public ResponseEntity<MentoringResponse> getMentoringClassInfo(
          @PathVariable("mentoring_class_id") Long mentoringClassId
  ){
    MentoringResponse mentoringResponse = mentorService.getMentorClassInfo(mentoringClassId);
    return ResponseEntity.ok(mentoringResponse);
  }

  @GetMapping("/mentoring/{mentoring_class_id}/apply")
  public ResponseEntity<MentoringScheduleResponse> getMentoringClassInfoInApply(
          @PathVariable("mentoring_class_id") Long mentoringClassId
  ){
    MentoringScheduleResponse mentoringScheduleResponse = mentorService.getMentorClassScheduleInfo(mentoringClassId);
    return ResponseEntity.ok(mentoringScheduleResponse);
  }
}
