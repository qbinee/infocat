/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain;

import backend.resumerryv2.global.domain.entity.BaseEntity;
import backend.resumerryv2.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class ClassSession extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private User user;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private MentorClass mentorClass;

  private LocalDateTime bookingDay;

  @Nullable private String major;

  private String selfIntroduce;

  private String preQuestion;

  @Nullable private String otherThings;

  private Boolean mentorChecked;

  private Boolean userDeprecated;

  @Column(length = 100)
  private String name;

  @Column(length = 20)
  private String phoneNumber;

  @Override
  public void prePersist() {
    super.prePersist();
    this.mentorChecked = this.mentorChecked == null ? false : this.mentorChecked;
    this.userDeprecated = this.userDeprecated == null ? false : this.userDeprecated;
  }
}
