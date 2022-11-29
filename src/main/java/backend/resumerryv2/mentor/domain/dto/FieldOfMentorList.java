package backend.resumerryv2.mentor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class FieldOfMentorList {
    private String field;
    private String category;
    private String sorted;
    private String title;
    public List<Integer> getFieldList(){
        return field == null ? null : convertStr2IntegerArray(field);
    }

    public List<Integer> getCategoryList(){
        return category == null ? null : convertStr2IntegerArray(category);
    }

    private static List<Integer> convertStr2IntegerArray(String s){
        return Arrays.stream(Stream.of(s.split(",")).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new)).toList();
    }

}
