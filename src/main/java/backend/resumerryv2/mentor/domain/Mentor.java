/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain;

import backend.resumerryv2.global.converter.CompanyCodeConverter;
import backend.resumerryv2.global.converter.FieldCodeConverter;
import backend.resumerryv2.global.converter.RoleCodeConverter;
import backend.resumerryv2.global.domain.entity.BaseEntity;
import backend.resumerryv2.util.domain.entity.Category;
import backend.resumerryv2.util.domain.enums.Company;
import backend.resumerryv2.util.domain.enums.Field;
import backend.resumerryv2.util.domain.enums.Role;
import backend.resumerryv2.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Getter
@NoArgsConstructor
@Entity
public class Mentor extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String title;

  @Convert(converter = RoleCodeConverter.class)
  private Role role;

  private Integer years;

  @Convert(converter = CompanyCodeConverter.class)
  private Company company;

  @Nullable private Float stars;

  private Integer views;

  @Convert(converter = FieldCodeConverter.class)
  private Field field;

  private Integer price;

  @Column(length = 100) private String email;

  @NotNull @Column(length = 1000) private String career;

  private String job;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "mentor_id")
  private Collection<MentorClass> classes;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "mentor_id")
  private Collection<backend.resumerryv2.util.domain.entity.Role> roles;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "mentor_id")
  private Collection<Category> categories;

}
