/* Licensed under InfoCat */
package backend.resumerryv2.mentor.domain.dto;

import backend.resumerryv2.category.domain.enums.Company;
import backend.resumerryv2.category.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MentorContent {
    private Long id;
    private String title;
    private Role role;
    private Company company;
    private Integer stars;
    private Integer years;
    private String image;
}
