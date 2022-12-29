/* Licensed under InfoCat */
package backend.resumerryv2.util.domain.repository;

import backend.resumerryv2.util.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
  @Override
  <S extends Role> S save(S entity);
}
