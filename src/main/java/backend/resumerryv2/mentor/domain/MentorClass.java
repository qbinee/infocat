/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain;

import backend.resumerryv2.global.domain.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MentorClass extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Mentor mentor;

  @Column(length = 200, nullable = false)
  private String title;

  @Column(length = 200, nullable = false)
  private String shorts;

  @Column(length = 5000, nullable = false)
  private String contents;

  @Column(length = 1000, nullable = false)
  private String career;

  private String image;

  private Integer price;

  private Integer viewCnt;

  @Builder
  public MentorClass(
      Long id,
      Mentor mentor,
      String title,
      String shorts,
      String contents,
      String career,
      String image,
      Integer price) {
    this.id = id;
    this.mentor = mentor;
    this.title = title;
    this.shorts = shorts;
    this.contents = contents;
    this.career = career;
    this.image = image;
    this.price = price;
  }

  @Override
  public void prePersist() {
    super.prePersist();
    this.viewCnt = this.viewCnt == null ? 0 : this.viewCnt;
  }
}
