/* Licensed under InfoCat */
package backend.resumerryv2.auth.domain.repository;

import backend.resumerryv2.auth.domain.EmailValidation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailValidationRepository extends JpaRepository<EmailValidation, Long> {

  @Override
  <S extends EmailValidation> S save(S entity);

  Optional<EmailValidation> findByEmailOrderByExpireTime(String email);

  @Override
  void delete(EmailValidation emailValidation);
}
