package backend.resumerryv2.mentor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FieldOfMentorList {
    private List<Integer> field;
    private List<Integer> category;
    private String sorted;
    private String title;
}
