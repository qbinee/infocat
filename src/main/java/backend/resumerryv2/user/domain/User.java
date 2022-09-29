package backend.resumerryv2.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    @Nullable
    private Integer years;

    @Nullable
    private Enum<UserCategory> category;

    @Nullable
    private String introduce;

    @Nullable
    private String imageSrc;

    @Builder
    public User(Long id, String email, String nickname, Integer years, Enum<UserCategory> category,
                String introduce, String imageSrc) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.years = years;
        this.category = category;
        this.introduce = introduce;
        this.imageSrc = imageSrc;
    }

}

