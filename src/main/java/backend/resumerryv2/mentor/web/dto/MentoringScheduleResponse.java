/* Licensed under InfoCat */
package backend.resumerryv2.mentor.web.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentoringScheduleResponse {
    private Long classId;
    private Long mentorId;
    private Character duration;
    private List<String> bookingDays;
    private List<String> totalDays;
}
