/* Licensed under InfoCat */
package backend.resumerryv2.user.domain.repository;

import static backend.resumerryv2.mentor.domain.QClassSession.classSession;
import static backend.resumerryv2.mentor.domain.QClassWeekSchedule.classWeekSchedule;
import static backend.resumerryv2.mentor.domain.QMentor.mentor;
import static backend.resumerryv2.mentor.domain.QMentorClass.mentorClass;

import backend.resumerryv2.global.enums.ClassSessionStatus;
import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.user.domain.dto.ClassSessionContent;
import backend.resumerryv2.user.web.dto.ClassSessionResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ClassSessionResponse> getClassSession(User user, Pageable pageable) {
        List<ClassSessionContent> classSessionContents =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        ClassSessionContent.class,
                                        classSession.id,
                                        mentorClass.title,
                                        mentor.name,
                                        classSession.userDeprecated,
                                        classSession.mentorChecked,
                                        classSession.mentorDeprecated,
                                        classSession.createdDate,
                                        classSession.bookingDay,
                                        classWeekSchedule.duration))
                        .from(classSession)
                        .innerJoin(mentorClass)
                        .on(mentorClass.id.eq(classSession.mentorClass.id))
                        .innerJoin(classWeekSchedule)
                        .on(classWeekSchedule.mentorClass.id.eq(classSession.mentorClass.id))
                        .innerJoin(mentor)
                        .on(mentor.id.eq(classSession.mentorClass.mentor.id))
                        .where(classSession.user.id.eq(user.getId()))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        long counts =
                jpaQueryFactory
                        .select(classSession.count())
                        .from(classSession)
                        .where(classSession.user.id.eq(user.getId()))
                        .fetchOne();

        return contentToResponse(classSessionContents, counts, pageable);
    }

    private Page<ClassSessionResponse> contentToResponse(
            List<ClassSessionContent> contents, Long counts, Pageable p) {
        List<ClassSessionResponse> classSessionResponses =
                contents.stream()
                        .map(
                                c ->
                                        new ClassSessionResponse(
                                                c.getClassSessionId(),
                                                c.getTitle(),
                                                c.getName(),
                                                getUserStatus(
                                                                c.getUserDeprecated(),
                                                                c.getMentorChecked(),
                                                                c.getMentorDeprecated(),
                                                                c.getBookingDay())
                                                        .getName(),
                                                c.getApplyDate().toLocalDate().toString(),
                                                c.getBookingDay().toString(),
                                                c.getDuration()))
                        .collect(Collectors.toList());
        return new PageImpl<>(classSessionResponses, p, counts);
    }

    private ClassSessionStatus getUserStatus(
            Boolean userDeprecated,
            Boolean mentorChecked,
            Boolean mentorDeprecated,
            LocalDateTime bookingDay) {

        if (bookingDay.isAfter(LocalDateTime.now())) {
            if (userDeprecated) {
                return ClassSessionStatus.USER_REFUSE;
            } else {
                if (mentorChecked) {
                    return ClassSessionStatus.ASSIGN;
                } else {
                    if (mentorDeprecated) {
                        return ClassSessionStatus.MENTOR_REFUSE;
                    } else {
                        return ClassSessionStatus.PENDING;
                    }
                }
            }
        } else {
            if (userDeprecated) {
                return ClassSessionStatus.USER_REFUSE;
            } else {
                if (mentorChecked) {
                    return ClassSessionStatus.COMPLETE;
                }
                return ClassSessionStatus.EXPIRED;
            }
        }
    }
}
