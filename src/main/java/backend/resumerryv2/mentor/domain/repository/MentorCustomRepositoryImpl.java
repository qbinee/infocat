package backend.resumerryv2.mentor.domain.repository;

import backend.resumerryv2.mentor.domain.Mentor;
import backend.resumerryv2.mentor.domain.dto.FieldOfMentorList;
import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.service.enums.Company;
import backend.resumerryv2.mentor.service.enums.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static backend.resumerryv2.mentor.domain.QMentor.mentor;

@RequiredArgsConstructor
@Repository
public class MentorCustomRepositoryImpl implements MentorCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Page<MentorContent> searchAll(FieldOfMentorList f, Pageable p){
        List<Mentor> mentors =  jpaQueryFactory
                .selectFrom(mentor)
                .offset(p.getOffset())
                .limit(p.getPageSize())
                .fetch();
        long counts = jpaQueryFactory
                .select(mentor.count())
                .from(mentor)
                .fetchOne();

        return entityToDTO(mentors, p, counts);
    }

    private PageImpl entityToDTO(List<Mentor> mentors, Pageable p, long counts){
        List<MentorContent> contents = mentors.stream().map(m -> new MentorContent(
                m.getId()
                , m.getTitle()
                , Role.of(m.getRole().intValue()).getName()
                , Company.of(m.getCompany().intValue()).getName()
                , m.getStars()
                , "test image") )
                .collect(Collectors.toList());
        return new PageImpl<>(contents, p, counts);
    }

}
