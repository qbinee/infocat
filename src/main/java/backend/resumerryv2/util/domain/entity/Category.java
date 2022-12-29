package backend.resumerryv2.util.domain.entity;

import backend.resumerryv2.global.converter.CategoryCodeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable @Column(name = "mentor_id") private Long mentorId;

    @Nullable @Column(name = "class_id") private Long classId;

    @Convert(converter = CategoryCodeConverter.class) private Integer code;

}
