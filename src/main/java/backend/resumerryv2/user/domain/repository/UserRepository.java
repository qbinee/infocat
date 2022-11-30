/* Licensed under InfoCat */
package backend.resumerryv2.user.domain.repository;

import backend.resumerryv2.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  @Override
  <S extends User> S save(S entity);

  Optional<User> findByEmail(String email);
}
