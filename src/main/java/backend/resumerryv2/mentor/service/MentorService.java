/* Licensed under InfoCat */
package backend.resumerryv2.mentor.service;

import backend.resumerryv2.exception.CustomException;
import backend.resumerryv2.exception.ErrorType;
import backend.resumerryv2.mentor.domain.ClassWeekSchedule;
import backend.resumerryv2.mentor.domain.Mentor;
import backend.resumerryv2.mentor.domain.MentorClass;
import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.domain.repository.ClassWeekScheduleRepository;
import backend.resumerryv2.mentor.domain.repository.MentorRepository;
import backend.resumerryv2.mentor.domain.repository.custom.MentorCustomRepository;
import backend.resumerryv2.mentor.web.dto.FieldOfMentorList;
import backend.resumerryv2.mentor.web.dto.MentorRequest;
import backend.resumerryv2.mentor.web.dto.MentoringRequest;
import backend.resumerryv2.security.CustomUserDetails;
import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.user.domain.repository.UserRepository;
import backend.resumerryv2.util.domain.entity.Role;
import backend.resumerryv2.util.domain.enums.Category;
import backend.resumerryv2.util.domain.enums.Company;
import backend.resumerryv2.util.domain.repository.CategoryRepository;
import backend.resumerryv2.util.domain.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class MentorService {
  private final MentorCustomRepository mentorCustomRepository;
  private final UserRepository userRepository;

  private final MentorRepository mentorRepository;

  private final ClassWeekScheduleRepository classWeekScheduleRepository;

  private final CategoryRepository categoryRepository;
  private final RoleRepository roleRepository;

  public Page<MentorContent> getMentorList(
      List<Integer> field, List<Integer> category, String sorted, String title, Pageable pageable) {
    FieldOfMentorList fieldOfMentorList = new FieldOfMentorList(field, category, sorted, title);
    return mentorCustomRepository.searchAll(fieldOfMentorList, pageable);
  }

  public boolean getUserIsMentor(User user) {
    if (mentorRepository.findAllByUserId(user.getId()).isPresent()) {
      return true;
    }
    return false;
  }

  public void createMentor(CustomUserDetails userDetails, MentorRequest mentorInfo) {
    User user = findUserByEmail(userDetails.getEmail());
    Company company = Company.of(mentorInfo.getEmail().split("@")[1]);

    Mentor mentor =
        Mentor.builder()
            .years(mentorInfo.getYears())
            .email(mentorInfo.getEmail())
            .career(mentorInfo.getCareer())
            .job(mentorInfo.getJob())
            .company(company)
            .phoneNumber(mentorInfo.getPhoneNumber())
            .name(mentorInfo.getName())
            .user(user)
            .build();

    try {
      mentorRepository.save(mentor);
    } catch (DataIntegrityViolationException e) {
      throw new CustomException(HttpStatus.SERVICE_UNAVAILABLE, ErrorType.DUPLICATED_MENTOR);
    }
  }

  public void createMentoringClass(CustomUserDetails userDetails, MentoringRequest mentoringInfo) {
    User user = findUserByEmail(userDetails.getEmail());
    Mentor mentor = findMentorByUser(user);

    MentorClass mentorClass =
        MentorClass.builder()
            .title(mentoringInfo.getTitle())
            .shorts(mentoringInfo.getShorts())
            .contents(mentoringInfo.getContent())
            .career(mentoringInfo.getCareer())
            .image(mentoringInfo.getImage())
            .price(mentoringInfo.getPrice())
            .mentor(mentor)
            .build();

    List<backend.resumerryv2.util.domain.entity.Category> categoryList =
        generateCategoryList(mentoringInfo.getField(), mentor, mentorClass);

    ClassWeekSchedule classWeekSchedule =
        ClassWeekSchedule.builder()
            .duration(mentoringInfo.getDuration())
            .timeSchedule(mentoringInfo.getTimes().toString())
            .mentorClass(mentorClass)
            .build();

    Role role =
        Role.builder()
            .mentor(mentor)
            .mentorClass(mentorClass)
            .role(backend.resumerryv2.util.domain.enums.Role.of(mentoringInfo.getRole()))
            .build();

    classWeekScheduleRepository.save(classWeekSchedule);
    categoryRepository.saveAll(categoryList);
    roleRepository.save(role);
  }

  private List<backend.resumerryv2.util.domain.entity.Category> generateCategoryList(
      List<Integer> categories, Mentor mentor, MentorClass mentorClass) {
    return categories.stream()
        .map(
            c ->
                backend.resumerryv2.util.domain.entity.Category.builder()
                    .category(Category.of(c))
                    .mentor(mentor)
                    .mentorClass(mentorClass)
                    .build())
        .toList();
  }

  private Mentor findMentorByUser(User user) {
    return mentorRepository
        .findAllByUserId(user.getId())
        .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_MENTOR));
  }

  private User findUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(() -> new CustomException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_USER));
  }
}
