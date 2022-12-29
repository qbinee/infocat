/* Licensed under InfoCat */
package backend.resumerryv2.mentor.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentoringRequest {
  private String title;
  private String shorts;
  private String content;
  private Integer role;
  private List<Integer> field;
  private String career;
  private String image;
  private Integer price;
  private List<String> times;
  private Character duration;
}
