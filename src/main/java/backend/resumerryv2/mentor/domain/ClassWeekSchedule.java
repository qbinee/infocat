package backend.resumerryv2.mentor.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;

@Getter
@NoArgsConstructor
@Entity
public class ClassWeekSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_id") private Long classId;

    private Time duration;

    private Integer timeSchedule;

    private Boolean isDelete;

}
