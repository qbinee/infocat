/* Licensed under InfoCat */
package backend.resumerryv2.util.domain.repository;

import backend.resumerryv2.util.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  @Override
  <S extends Category> List<S> saveAll(Iterable<S> entities);
}
