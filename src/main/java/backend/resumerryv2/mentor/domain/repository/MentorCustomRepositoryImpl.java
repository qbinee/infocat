package backend.resumerryv2.mentor.domain.repository;

import backend.resumerryv2.mentor.domain.Mentor;
import backend.resumerryv2.mentor.domain.dto.FieldOfMentorList;
import backend.resumerryv2.mentor.domain.dto.MentorContent;
import backend.resumerryv2.mentor.service.enums.Company;
import backend.resumerryv2.mentor.service.enums.Field;
import backend.resumerryv2.mentor.service.enums.Role;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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
    public Page<MentorContent> searchAll(Pageable p){
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

    @Override
    public Page<MentorContent> searchOfFilter(FieldOfMentorList f, Pageable p) {

        List<Mentor> mentors = jpaQueryFactory
                .selectFrom(mentor)
                .where(eqCategories(f.getCategoryList())
                        ,eqField(f.getFieldList())
                )
                .offset(p.getOffset())
                .limit(p.getPageSize())
                .orderBy(getSorted(f.getSorted()))
                .fetch();
        long counts = jpaQueryFactory
                .select(mentor.count())
                .from(mentor)
                .where(eqCategories(f.getCategoryList())
                        ,eqField(f.getFieldList())
                )
                .fetchOne();

        return entityToDTO(mentors, p, counts);

    }

    private BooleanBuilder eqCategories(List<Integer> categories){
        if (categories == null){
            return null;
        }
        BooleanBuilder builder = new BooleanBuilder();
        for (Integer c : categories) {
            builder.or(mentor.role.eq(Role.of(c)));
        }
        return builder;
    }

    private BooleanBuilder eqField(List<Integer> fields){
        if (fields == null){
            return null;
        }
        BooleanBuilder builder = new BooleanBuilder();
        for (Integer f : fields) {
            builder.or(mentor.field.eq(Field.of(f)));
        }
        return builder;
    }

    private OrderSpecifier getSorted(String sorted){
        if (sorted == "recent"){
            return new OrderSpecifier(Order.DESC, mentor.modifiedDate);
        }else if(sorted == "popular") {
            return new OrderSpecifier(Order.DESC, mentor.views);
        }else if(sorted == "stars"){
            return new OrderSpecifier(Order.DESC, mentor.stars);
        }else if(sorted == "low_price"){
            // todo: price 별로 바꾸기
            return new OrderSpecifier(Order.DESC, mentor.id);
        }else if(sorted == "high_price"){
            return new OrderSpecifier(Order.DESC, mentor.id);
        }
        return new OrderSpecifier(Order.DESC, mentor.modifiedDate);
    }

    private PageImpl entityToDTO(List<Mentor> mentors, Pageable p, long counts){
        List<MentorContent> contents = mentors.stream().map(m -> new MentorContent(
                m.getId()
                , m.getTitle()
                , Role.of(m.getRole().getCode()).getName()
                , Company.of(m.getCompany().intValue()).getName()
                , m.getStars()
                , "test image") )
                .collect(Collectors.toList());
        return new PageImpl<>(contents, p, counts);
    }

}
