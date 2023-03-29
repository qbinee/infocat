/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain.repository;

import backend.resumerryv2.mentor.domain.ClassSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassSessionRepository extends JpaRepository<ClassSession, Long> {
    @Override
    <S extends ClassSession> S save(S entity);
}
