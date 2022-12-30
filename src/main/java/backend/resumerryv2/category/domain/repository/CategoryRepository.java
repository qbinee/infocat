/* Licensed under InfoCat */
package backend.resumerryv2.category.domain.repository;

import backend.resumerryv2.category.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  @Override
  <S extends Category> List<S> saveAll(Iterable<S> entities);
}
