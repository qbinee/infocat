/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain.repository;

import backend.resumerryv2.mentor.domain.MentorClass;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorClassRepository extends JpaRepository<MentorClass, Long> {
    @Override
    <S extends MentorClass> S save(S entity);

    @Override
    Optional<MentorClass> findById(Long aLong);
}
