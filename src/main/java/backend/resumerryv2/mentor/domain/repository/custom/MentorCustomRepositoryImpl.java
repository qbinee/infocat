/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain.repository.custom;

import static backend.resumerryv2.category.domain.entity.QCategory.category1;
import static backend.resumerryv2.category.domain.entity.QRole.role1;
import static backend.resumerryv2.mentor.domain.QClassSession.classSession;
import static backend.resumerryv2.mentor.domain.QClassWeekSchedule.classWeekSchedule;
import static backend.resumerryv2.mentor.domain.QMentor.mentor;
import static backend.resumerryv2.mentor.domain.QMentorClass.mentorClass;
import static backend.resumerryv2.user.domain.QUser.user;

import backend.resumerryv2.category.domain.enums.Category;
import backend.resumerryv2.category.domain.enums.Role;
import backend.resumerryv2.global.enums.ClassSessionStatus;
import backend.resumerryv2.mentor.domain.Mentor;
import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.domain.dto.MentoringContent;
import backend.resumerryv2.mentor.domain.dto.MentoringPost;
import backend.resumerryv2.mentor.web.dto.FieldOfMentorList;
import backend.resumerryv2.user.domain.dto.ClassSessionContent;
import backend.resumerryv2.user.web.dto.ClassSessionResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MentorCustomRepositoryImpl implements MentorCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<MentorContent> searchAll(FieldOfMentorList f, Pageable p) {

        List<Long> roles = findClassIdByRole(f.getCategory());
        List<Long> categories = findClassIdByCategory(f.getField());

        List<Long> mentorClassIds =
                roles.stream()
                        .filter(old -> categories.stream().anyMatch(Predicate.isEqual(old)))
                        .collect(Collectors.toList());

        List<MentorContent> mentorClassList =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        MentorContent.class,
                                        mentorClass.id,
                                        mentorClass.title,
                                        role1.role,
                                        mentor.company,
                                        mentor.years,
                                        mentor.years,
                                        mentorClass.image))
                        .from(mentorClass)
                        .leftJoin(mentor)
                        .on(mentor.id.eq(mentorClass.mentor.id))
                        .leftJoin(role1)
                        .on(role1.mentorClass.id.eq(mentorClass.id))
                        .where(
                                eqMentorClassIds(mentorClassIds),
                                eqTitle(f.getTitle()),
                                mentor.user.isDelete.isFalse())
                        .offset(p.getOffset())
                        .limit(p.getPageSize())
                        .orderBy(getSorted(f.getSorted()))
                        .fetch();

        long counts =
                jpaQueryFactory
                        .select(mentorClass.count())
                        .from(mentorClass)
                        .where(eqMentorClassIds(mentorClassIds), eqTitle(f.getTitle()))
                        .fetchOne();

        return entityToDTO(mentorClassList, p, counts);
    }

    @Override
    public MentoringContent getMentoringClassInfo(Long mentoringClassId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                MentoringContent.class,
                                mentorClass.id,
                                mentor.id,
                                mentor.name,
                                mentorClass.title,
                                mentorClass.shorts,
                                mentorClass.contents,
                                role1.role,
                                mentor.job,
                                mentorClass.career,
                                mentor.years,
                                mentor.company,
                                mentor.stars,
                                mentorClass.image,
                                mentorClass.price))
                .from(mentorClass)
                .innerJoin(mentor)
                .on(mentor.id.eq(mentorClass.mentor.id))
                .leftJoin(role1)
                .on(role1.mentorClass.id.eq(mentorClass.id))
                .where(mentorClass.id.eq(mentoringClassId))
                .fetchOne();
    }

    @Override
    public List<String> getBookingDaysByMentoringClassId(Long mentoringClassId) {
        return jpaQueryFactory
                .select(classSession.bookingDay)
                .from(classSession)
                .where(
                        classSession.mentorClass.id.eq(mentoringClassId),
                        classSession.bookingDay.after(LocalDateTime.now()))
                .fetch()
                .stream()
                .map(b -> b.toString() + ":00.000Z")
                .collect(Collectors.toList());
    }

    @Override
    public Page<ClassSessionResponse> getClassSession(Mentor mentor, Pageable pageable) {
        List<ClassSessionContent> classSessionContents =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        ClassSessionContent.class,
                                        classSession.id,
                                        mentorClass.title,
                                        user.nickname,
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
                        .innerJoin(user)
                        .on(user.id.eq(classSession.user.id))
                        .where(classSession.mentorClass.mentor.id.eq(mentor.getId()))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        long counts =
                jpaQueryFactory
                        .select(classSession.count())
                        .from(classSession)
                        .innerJoin(mentorClass)
                        .on(mentorClass.id.eq(classSession.mentorClass.id))
                        .where(mentorClass.mentor.id.eq(mentor.getId()))
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
                                                getMentorStatus(
                                                                c.getUserDeprecated(),
                                                                c.getMentorChecked(),
                                                                c.getMentorDeprecated(),
                                                                c.getBookingDay())
                                                        .getName(),
                                                c.getApplyDate().toLocalDate().toString(),
                                                c.getBookingDay().toString() + ":00.000Z",
                                                c.getDuration()))
                        .collect(Collectors.toList());
        return new PageImpl<>(classSessionResponses, p, counts);
    }

    private ClassSessionStatus getMentorStatus(
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
                    return ClassSessionStatus.PENDING;
                }
            }
        } else {
            if (userDeprecated) {
                return ClassSessionStatus.USER_REFUSE;
            } else {
                return ClassSessionStatus.COMPLETE;
            }
        }
    }

    private List<Long> findClassIdByRole(List<Integer> i) {
        return jpaQueryFactory.select(role1.mentorClass.id).from(role1).where(eqRole(i)).fetch();
    }

    private List<Long> findClassIdByCategory(List<Integer> i) {
        return jpaQueryFactory
                .select(category1.mentorClass.id)
                .from(category1)
                .where(eqCategories(i))
                .fetch();
    }

    private BooleanExpression eqTitle(String title) {
        return title == null ? null : mentorClass.title.contains(title);
    }

    private BooleanBuilder eqRole(List<Integer> roles) {
        if (roles == null) {
            return null;
        }
        BooleanBuilder builder = new BooleanBuilder();
        for (Integer r : roles) {
            builder.or(role1.role.eq(Role.of(r)));
        }
        return builder;
    }

    private BooleanBuilder eqCategories(List<Integer> categories) {
        if (categories == null) {
            return null;
        }
        BooleanBuilder builder = new BooleanBuilder();
        for (Integer c : categories) {
            builder.or(category1.category.eq(Category.of(c)));
        }
        return builder;
    }

    private BooleanBuilder eqMentorClassIds(List<Long> classIds) {
        if (classIds == null) {
            return null;
        }
        BooleanBuilder builder = new BooleanBuilder();
        for (Long c : classIds) {
            builder.or(mentorClass.id.eq(c));
        }
        return builder;
    }

    private OrderSpecifier getSorted(String sorted) {
        if (sorted == "recent") {
            return new OrderSpecifier(Order.DESC, mentorClass.createdDate);
        } else if (sorted == "popular") {
            return new OrderSpecifier(Order.DESC, mentor.views);
        } else if (sorted == "stars") {
            return new OrderSpecifier(Order.DESC, mentor.stars);
        } else if (sorted == "low_price") {
            return new OrderSpecifier(Order.ASC, mentorClass.price);
        } else if (sorted == "high_price") {
            return new OrderSpecifier(Order.DESC, mentorClass.price);
        }
        return new OrderSpecifier(Order.DESC, mentorClass.createdDate);
    }

    private PageImpl entityToDTO(List<MentorContent> mentorClasses, Pageable p, long counts) {
        List<MentoringPost> contents =
                mentorClasses.stream()
                        .map(
                                m ->
                                        new MentoringPost(
                                                m.getId(),
                                                m.getTitle(),
                                                m.getRole().getName(),
                                                m.getCompany().getName(),
                                                0,
                                                m.getYears(),
                                                m.getImage()))
                        .collect(Collectors.toList());
        return new PageImpl<>(contents, p, counts);
    }
}
