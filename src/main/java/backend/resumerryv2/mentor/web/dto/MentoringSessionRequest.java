/* Licensed under InfoCat */
package backend.resumerryv2.mentor.web.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MentoringSessionRequest {
    private Long mentoringId;
    private String name;
    private String phone;
    private String userCondition;
    private String major;
    private LocalDateTime schedule;
    private String introduce;
    private String questions;
    private String wanted;
}
