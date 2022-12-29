/* Licensed under InfoCat */
package backend.resumerryv2.mentor.service;

import backend.resumerryv2.exception.CustomException;
import backend.resumerryv2.exception.ErrorType;
import backend.resumerryv2.mentor.domain.Mentor;
import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.domain.repository.MentorCustomRepository;
import backend.resumerryv2.mentor.domain.repository.MentorRepository;
import backend.resumerryv2.mentor.web.dto.FieldOfMentorList;
import backend.resumerryv2.mentor.web.dto.MentorRequest;
import backend.resumerryv2.security.CustomUserDetails;
import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.user.domain.repository.UserRepository;
import backend.resumerryv2.util.domain.enums.Company;
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

    public void createMentor(CustomUserDetails userDetails, MentorRequest mentorInfo) {
        User user = findUserByEmail(userDetails.getEmail());
        Company company = Company.of(mentorInfo.getEmail().split("@")[1]);

        Mentor mentor = Mentor.builder()
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

    private User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () ->
                                new CustomException(
                                        HttpStatus.BAD_REQUEST, ErrorType.INVALID_USER)
                );
    }

}
