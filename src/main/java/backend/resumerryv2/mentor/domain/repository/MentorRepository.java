/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain.repository;

import backend.resumerryv2.mentor.domain.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, Long> {
  Optional<Mentor> findAllByUserId(Long id);

  Mentor save(Mentor mentor);
}
