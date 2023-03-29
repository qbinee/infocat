/* Licensed under InfoCat */
package backend.resumerryv2.user.domain;

import backend.resumerryv2.global.domain.entity.BaseEntity;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    @Nullable private Integer years;

    @Nullable private Enum<UserCategory> category;

    @Nullable
    @Column(length = 200)
    private String introduce;

    @Nullable private String imageSrc;

    @Builder
    public User(
            Long id,
            String email,
            String nickname,
            String password,
            @Nullable Integer years,
            @Nullable Enum<UserCategory> category,
            @Nullable String introduce,
            @Nullable String imageSrc) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.years = years;
        this.category = category;
        this.introduce = introduce;
        this.imageSrc = imageSrc;
    }
}
