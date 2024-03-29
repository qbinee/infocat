/* Licensed under InfoCat */
package backend.resumerryv2.mentor.service;

import backend.resumerryv2.category.domain.entity.Category;
import backend.resumerryv2.category.domain.entity.Role;
import backend.resumerryv2.category.domain.enums.Company;
import backend.resumerryv2.category.domain.repository.CategoryRepository;
import backend.resumerryv2.category.domain.repository.RoleRepository;
import backend.resumerryv2.exception.CustomException;
import backend.resumerryv2.exception.ErrorType;
import backend.resumerryv2.mentor.domain.ClassSession;
import backend.resumerryv2.mentor.domain.ClassWeekSchedule;
import backend.resumerryv2.mentor.domain.Mentor;
import backend.resumerryv2.mentor.domain.MentorClass;
import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.domain.dto.MentoringContent;
import backend.resumerryv2.mentor.domain.repository.ClassSessionRepository;
import backend.resumerryv2.mentor.domain.repository.ClassWeekScheduleRepository;
import backend.resumerryv2.mentor.domain.repository.MentorClassRepository;
import backend.resumerryv2.mentor.domain.repository.MentorRepository;
import backend.resumerryv2.mentor.domain.repository.custom.MentorCustomRepository;
import backend.resumerryv2.mentor.web.dto.*;
import backend.resumerryv2.security.CustomUserDetails;
import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.user.domain.repository.UserRepository;
import backend.resumerryv2.user.web.dto.ClassSessionResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class MentorService {
    private final MentorCustomRepository mentorCustomRepository;
    private final UserRepository userRepository;

    private final MentorRepository mentorRepository;
    private final MentorClassRepository mentorClassRepository;
    private final ClassSessionRepository classSessionRepository;

    private final ClassWeekScheduleRepository classWeekScheduleRepository;

    private final CategoryRepository categoryRepository;
    private final RoleRepository roleRepository;

    public Page<MentorContent> getMentorList(
            List<Integer> field,
            List<Integer> category,
            String sorted,
            String title,
            Pageable pageable) {
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
        if (mentorRepository.findAllByUserId(user.getId()).isPresent()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ErrorType.DUPLICATED_MENTOR);
        }
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
        mentorRepository.save(mentor);
    }

    public MentorResponse getMentor(CustomUserDetails customUserDetails) {
        User user = findUserByEmail(customUserDetails.getEmail());
        Mentor mentor = findMentorByUser(user);
        return new MentorResponse(
                mentor.getYears(),
                mentor.getEmail(),
                mentor.getCareer(),
                mentor.getJob(),
                mentor.getPhoneNumber(),
                mentor.getName(),
                mentor.getCompany().getName());
    }

    public void createMentoringClass(
            CustomUserDetails userDetails, MentoringRequest mentoringInfo) {
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

        List<Category> categoryList =
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
                        .role(
                                backend.resumerryv2.category.domain.enums.Role.of(
                                        mentoringInfo.getRole()))
                        .build();

        classWeekScheduleRepository.save(classWeekSchedule);
        categoryRepository.saveAll(categoryList);
        roleRepository.save(role);
    }

    public void createMentoringSession(
            CustomUserDetails userDetails, MentoringSessionRequest mentoringSessionRequest) {
        User user = findUserByEmail(userDetails.getEmail());
        MentorClass mentorClass = findMentorClassbyId(mentoringSessionRequest.getMentoringId());

        ClassSession classSession =
                ClassSession.builder()
                        .user(user)
                        .mentorClass(mentorClass)
                        .bookingDay(mentoringSessionRequest.getSchedule())
                        .major(mentoringSessionRequest.getMajor())
                        .selfIntroduce(mentoringSessionRequest.getIntroduce())
                        .preQuestion(mentoringSessionRequest.getQuestions())
                        .otherThings(mentoringSessionRequest.getWanted())
                        .name(mentoringSessionRequest.getName())
                        .phoneNumber(mentoringSessionRequest.getPhone())
                        .userCondition(mentoringSessionRequest.getUserCondition())
                        .build();

        classSessionRepository.save(classSession);
    }

    public MentoringResponse getMentorClassInfo(Long mentoringClassId) {
        MentoringContent mentoringInfo =
                mentorCustomRepository.getMentoringClassInfo(mentoringClassId);
        return new MentoringResponse(
                mentoringClassId,
                mentoringInfo.getMentorId(),
                mentoringInfo.getNickname(),
                mentoringInfo.getTitle(),
                mentoringInfo.getShorts(),
                mentoringInfo.getContent(),
                mentoringInfo.getRole().getName(),
                mentoringInfo.getJob(),
                mentoringInfo.getCareer(),
                mentoringInfo.getYears(),
                mentoringInfo.getCompany().getName(),
                mentoringInfo.getStars(),
                mentoringInfo.getImage(),
                mentoringInfo.getPrice());
    }

    public MentoringScheduleResponse getMentorClassScheduleInfo(Long mentoringClassId) {
        List<String> bookingDays =
                mentorCustomRepository.getBookingDaysByMentoringClassId(mentoringClassId);
        ClassWeekSchedule weekSchedule = findClassScheduleByClassId(mentoringClassId);
        List<String> totalDays = extractTotalPossibleDate(weekSchedule);
        return new MentoringScheduleResponse(
                mentoringClassId,
                findMentorClassbyId(mentoringClassId).getMentor().getId(),
                weekSchedule.getDuration(),
                bookingDays,
                totalDays);
    }

    public Page<ClassSessionResponse> getMentorClassSession(
            CustomUserDetails userDetails, Pageable pageable) {
        User user = findUserByEmail(userDetails.getEmail());
        Mentor mentor = findMentorByUser(user);
        return mentorCustomRepository.getClassSession(mentor, pageable);
    }

    private List<String> extractTotalPossibleDate(ClassWeekSchedule classWeekSchedule) {
        String timeScheduleStr = classWeekSchedule.getTimeSchedule();
        List<String> totalPossibleDate = new ArrayList<>();

        LocalDate now = LocalDate.now();
        now.plusDays(3);
        List<LocalDate> availableDays =
                now.datesUntil(now.plusDays(11), Period.ofDays(1)).collect(Collectors.toList());

        String[] timeSchedule =
                timeScheduleStr.substring(1, timeScheduleStr.length() - 1).split(", ");

        for (LocalDate d : availableDays) {
            Integer week = parseNumberByDayOfWeek(d.getDayOfWeek());
            for (String t : timeSchedule) {
                String[] weeklyTime = t.split(":");
                if (week == Integer.parseInt(weeklyTime[0])) {
                    totalPossibleDate.add(
                            d.toString()
                                    + "T"
                                    + weeklyTime[1]
                                    + ":"
                                    + weeklyTime[2]
                                    + ":00.000Z"); // 12:30
                }
            }
        }
        return totalPossibleDate;
    }

    private Integer parseNumberByDayOfWeek(DayOfWeek n) {
        if (n == DayOfWeek.MONDAY) {
            return 1;
        } else if (n == DayOfWeek.TUESDAY) {
            return 2;
        } else if (n == DayOfWeek.WEDNESDAY) {
            return 3;
        } else if (n == DayOfWeek.THURSDAY) {
            return 4;
        } else if (n == DayOfWeek.FRIDAY) {
            return 5;
        } else if (n == DayOfWeek.SATURDAY) {
            return 6;
        }
        return 7;
    }

    private ClassWeekSchedule findClassScheduleByClassId(Long id) {
        return classWeekScheduleRepository
                .findAllByMentorClassId(id)
                .orElseThrow(
                        () ->
                                new CustomException(
                                        HttpStatus.BAD_REQUEST, ErrorType.INVALID_CLASS_SCHEDULE));
    }

    private List<Category> generateCategoryList(
            List<Integer> categories, Mentor mentor, MentorClass mentorClass) {
        return categories.stream()
                .map(
                        c ->
                                Category.builder()
                                        .category(
                                                backend.resumerryv2.category.domain.enums.Category
                                                        .of(c))
                                        .mentor(mentor)
                                        .mentorClass(mentorClass)
                                        .build())
                .collect(Collectors.toList());
    }

    private Mentor findMentorByUser(User user) {
        return mentorRepository
                .findAllByUserId(user.getId())
                .orElseThrow(
                        () ->
                                new CustomException(
                                        HttpStatus.BAD_REQUEST, ErrorType.INVALID_MENTOR));
    }

    private User findUserByEmail(String email) {
        return userRepository
                .findByEmailAndIsDeleteFalse(email)
                .orElseThrow(
                        () -> new CustomException(HttpStatus.BAD_REQUEST, ErrorType.INVALID_USER));
    }

    private MentorClass findMentorClassbyId(Long id) {
        return mentorClassRepository
                .findById(id)
                .orElseThrow(
                        () ->
                                new CustomException(
                                        HttpStatus.BAD_REQUEST, ErrorType.INVALID_MENTOR_CLASS));
    }
}
