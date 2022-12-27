package backend.resumerryv2.mentor.domain;

import backend.resumerryv2.global.domain.entity.BaseEntity;
import backend.resumerryv2.util.domain.entity.Category;
import backend.resumerryv2.util.domain.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Getter
@NoArgsConstructor
@Entity
public class MentorClass extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mentor_id") private Long mentorId;

    @Column(length = 200) private String title;

    @Column(length = 200) private String shorts;

    @Column(length = 5000) private String contents;

    @Column(length = 1000) private String career;

    private Integer price;

    private Integer viewCnt;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private Collection<ClassWeekSchedule> classWeekSchedules;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private Collection<ClassSession> classSessions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private Collection<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "class_id")
    private Collection<Category> categories;

    @Override
    public void prePersist(){
        super.prePersist();
        this.viewCnt = this.viewCnt == null ? 0 : this.viewCnt;
    }

}
