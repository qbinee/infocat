/* Licensed under InfoCat */
package backend.resumerryv2.auth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class EmailValidation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private Integer validationCode;

  private LocalDateTime expireTime;

  @Builder
  public EmailValidation(Long id, String email, Integer validationCode, LocalDateTime expireTime) {
    this.id = id;
    this.email = email;
    this.validationCode = validationCode;
    this.expireTime = expireTime;
  }
}
