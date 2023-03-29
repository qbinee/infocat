/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain;

import backend.resumerryv2.global.converter.BooleanConverter;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ClassWeekSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private MentorClass mentorClass;

    private Character duration;

    private String timeSchedule;

    @Convert(converter = BooleanConverter.class)
    private Boolean isDelete;

    @Builder
    public ClassWeekSchedule(
            Long id, MentorClass mentorClass, Character duration, String timeSchedule) {
        this.id = id;
        this.mentorClass = mentorClass;
        this.duration = duration;
        this.timeSchedule = timeSchedule;
    }

    @PrePersist
    public void prePersist() {
        this.isDelete = this.isDelete == null ? false : this.isDelete;
    }
}
