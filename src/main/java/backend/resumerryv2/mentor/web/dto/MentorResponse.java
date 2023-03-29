/* Licensed under InfoCat */
package backend.resumerryv2.mentor.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentorResponse {
    private Integer years;
    private String email;
    private String career;
    private String job;
    private String phoneNumber;
    private String name;
    private String company;
}
