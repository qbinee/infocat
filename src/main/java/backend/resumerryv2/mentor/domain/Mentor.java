/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain;

import backend.resumerryv2.global.converter.CompanyCodeConverter;
import backend.resumerryv2.global.domain.entity.BaseEntity;
import backend.resumerryv2.user.domain.User;
import backend.resumerryv2.util.domain.entity.Category;
import backend.resumerryv2.util.domain.enums.Company;
import backend.resumerryv2.util.domain.enums.Field;
import backend.resumerryv2.util.domain.enums.Role;
import lombok.Builder;
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

    @Deprecated
    @Nullable
    private String title;

    @Deprecated
    @Nullable
    private Role role;

    private Integer years;

    @Convert(converter = CompanyCodeConverter.class)
    private Company company;

    @Deprecated
    @Nullable
    private Float stars;

    @Deprecated
    @Nullable
    private Integer views;

    @Deprecated
    @Nullable
    private Field field;

    @Deprecated
    @Nullable
    private Integer price;

    @Column(length = 100)
    private String email;

    @NotNull
    @Column(length = 1000)
    private String career;

    private String job;

    private String name;

    private String phoneNumber;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_id")
    private Collection<MentorClass> classes;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_id")
    private Collection<backend.resumerryv2.util.domain.entity.Role> roles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_id")
    private Collection<Category> categories;

    @Builder
    public Mentor(Long id, User user, Integer years, String email, String career, String job, Company company, String phoneNumber, String name) {
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
