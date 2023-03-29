/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain;

import backend.resumerryv2.global.converter.BooleanConverter;
import backend.resumerryv2.global.domain.entity.BaseEntity;
import backend.resumerryv2.user.domain.User;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@Entity
public class ClassSession extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private MentorClass mentorClass;

    private LocalDateTime bookingDay;

    @Nullable private String major;

    private String selfIntroduce;

    private String preQuestion;

    @Nullable private String otherThings;

    @Convert(converter = BooleanConverter.class)
    private Boolean mentorChecked;

    @Convert(converter = BooleanConverter.class)
    private Boolean userDeprecated;

    @Convert(converter = BooleanConverter.class)
    private Boolean mentorDeprecated;

    @Column(length = 100)
    private String name;

    @Column(length = 20)
    private String phoneNumber;

    private String userCondition;

    @Override
    public void prePersist() {
        super.prePersist();
        this.mentorChecked = this.mentorChecked == null ? false : this.mentorChecked;
        this.userDeprecated = this.userDeprecated == null ? false : this.userDeprecated;
        this.mentorDeprecated = this.mentorDeprecated == null ? false : this.mentorDeprecated;
    }

    @Builder
    public ClassSession(
            Long id,
            User user,
            MentorClass mentorClass,
            LocalDateTime bookingDay,
            String major,
            String selfIntroduce,
            String preQuestion,
            String otherThings,
            String name,
            String phoneNumber,
            String userCondition) {
        this.id = id;
        this.user = user;
        this.mentorClass = mentorClass;
        this.bookingDay = bookingDay;
        this.major = major;
        this.selfIntroduce = selfIntroduce;
        this.preQuestion = preQuestion;
        this.otherThings = otherThings;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userCondition = userCondition;
    }
}
