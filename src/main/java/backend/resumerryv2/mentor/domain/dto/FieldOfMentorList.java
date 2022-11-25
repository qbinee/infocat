package backend.resumerryv2.mentor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldOfMentorList {
    private String field;
    private String role;
    private String sorted;
    // todo :: string -> int array로 변환
}
