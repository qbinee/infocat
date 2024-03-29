/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain.repository;

import backend.resumerryv2.mentor.domain.ClassWeekSchedule;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassWeekScheduleRepository extends JpaRepository<ClassWeekSchedule, Long> {
    @Override
    <S extends ClassWeekSchedule> S save(S entity);

    Optional<ClassWeekSchedule> findAllByMentorClassId(Long id);
}
