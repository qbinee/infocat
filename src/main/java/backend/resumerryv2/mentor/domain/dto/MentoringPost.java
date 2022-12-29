package backend.resumerryv2.mentor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MentoringPost {
    private Long id;
    private String title;
    private String role;
    private String company;
    private Integer stars;
    private Integer years;
    private String image;
}
