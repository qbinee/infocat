package backend.resumerryv2.mentor.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentoringResponse {
    private Long classId;
    private Long mentorId;
    private String nickname;
    private String title;
    private String shorts;
    private String content;
    private String role;
    private String job;
    private String career;
    private Integer years;
    private String company;
    private Float stars;
    private String image;
    private Integer price;

}
