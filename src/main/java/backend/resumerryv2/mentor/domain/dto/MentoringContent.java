package backend.resumerryv2.mentor.domain.dto;

import backend.resumerryv2.util.domain.enums.Company;
import backend.resumerryv2.util.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentoringContent {
    private Long classId;
    private Long mentorId;
    private String nickname;
    private String title;
    private String shorts;
    private String content;
    private Role role;
    private String career;
    private Integer years;
    private Company company;
    private Float stars;
    private String image;
    private Integer price;
}
