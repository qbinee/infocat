/* Licensed under InfoCat */
package backend.resumerryv2.mentor.web.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldOfMentorList {
    private List<Integer> field;
    private List<Integer> category;
    private String sorted;
    private String title;
}
