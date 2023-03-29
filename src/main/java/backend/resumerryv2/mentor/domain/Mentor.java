/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain;

import backend.resumerryv2.category.domain.enums.Company;
import backend.resumerryv2.category.domain.enums.Field;
import backend.resumerryv2.category.domain.enums.Role;
import backend.resumerryv2.global.converter.CompanyCodeConverter;
import backend.resumerryv2.global.domain.entity.BaseEntity;
import backend.resumerryv2.user.domain.User;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@Entity
public class Mentor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne private User user;

    @Deprecated @Nullable private String title;

    @Deprecated @Nullable private Role role;

    private Integer years;

    @Convert(converter = CompanyCodeConverter.class)
    private Company company;

    @Deprecated @Nullable private Float stars;

    @Deprecated @Nullable private Integer views;

    @Deprecated @Nullable private Field field;

    @Deprecated @Nullable private Integer price;

    @Column(length = 100)
    private String email;

    @NotNull
    @Column(length = 1000)
    private String career;

    private String job;

    private String name;

    private String phoneNumber;

    @Builder
    public Mentor(
            Long id,
            User user,
            Integer years,
            String email,
            String career,
            String job,
            Company company,
            String phoneNumber,
            String name) {
        this.id = id;
        this.user = user;
        this.years = years;
        this.email = email;
        this.career = career;
        this.job = job;
        this.company = company;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }
}
