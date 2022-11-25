package backend.resumerryv2.mentor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MentorContent {
   private long id;
   private String title;
   private String role;
   private String company;
   private float stars;
   private String image;
}
