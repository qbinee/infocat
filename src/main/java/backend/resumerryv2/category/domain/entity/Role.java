/* Licensed under InfoCat */
package backend.resumerryv2.category.domain.entity;

import backend.resumerryv2.global.converter.RoleCodeConverter;
import backend.resumerryv2.mentor.domain.Mentor;
import backend.resumerryv2.mentor.domain.MentorClass;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn
  private Mentor mentor;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn
  private MentorClass mentorClass;

  @Convert(converter = RoleCodeConverter.class)
  private backend.resumerryv2.category.domain.enums.Role role;

  @Builder
  public Role(
      Long id,
      Mentor mentor,
      MentorClass mentorClass,
      backend.resumerryv2.category.domain.enums.Role role) {
    this.id = id;
    this.mentor = mentor;
    this.mentorClass = mentorClass;
    this.role = role;
  }
}
