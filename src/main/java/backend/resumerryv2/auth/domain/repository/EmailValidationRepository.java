package backend.resumerryv2.auth.domain.repository;

import backend.resumerryv2.auth.domain.EmailValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailValidationRepository extends JpaRepository<EmailValidation, Long> {

    @Override
    <S extends EmailValidation> S save(S entity);

    Optional<EmailValidation> findByEmailOrderByExpireTime(String email);
}
