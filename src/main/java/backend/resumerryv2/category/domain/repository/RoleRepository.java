/* Licensed under InfoCat */
package backend.resumerryv2.category.domain.repository;

import backend.resumerryv2.category.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Override
    <S extends Role> S save(S entity);
}
