package backend.resumerryv2.mentor.domain;

import backend.resumerryv2.global.converter.FieldCodeConverter;
import backend.resumerryv2.global.converter.RoleCodeConverter;
import backend.resumerryv2.global.entity.BaseEntity;
import backend.resumerryv2.mentor.service.enums.Field;
import backend.resumerryv2.mentor.service.enums.Role;
import backend.resumerryv2.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

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

    private Integer company;

    @Nullable private Float stars;

    private Integer views;

    @Convert(converter = FieldCodeConverter.class)
    private Field field;

    private Integer price;
}
