/* Licensed under InfoCat */
package backend.resumerryv2.user.domain;

import backend.resumerryv2.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String nickname;

  private String password;

  @Nullable private Integer years;

  @Nullable private Enum<UserCategory> category;

  @Nullable private String introduce;

  @Nullable private String imageSrc;

  @Builder
  public User(
      Long id,
      String email,
      String nickname,
      String password,
      @Nullable Integer years,
      @Nullable Enum<UserCategory> category,
      @Nullable String introduce,
      @Nullable String imageSrc) {
    this.id = id;
    this.email = email;
    this.nickname = nickname;
    this.password = password;
    this.years = years;
    this.category = category;
    this.introduce = introduce;
    this.imageSrc = imageSrc;
  }
}
