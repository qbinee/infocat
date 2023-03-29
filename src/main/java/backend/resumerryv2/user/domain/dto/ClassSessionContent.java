/* Licensed under InfoCat */
package backend.resumerryv2.user.domain.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassSessionContent {
    private Long classSessionId;
    private String title;
    private String name;
    private Boolean userDeprecated;
    private Boolean mentorChecked;
    private Boolean mentorDeprecated;
    private LocalDateTime applyDate;
    private LocalDateTime bookingDay;
    private Character duration;
}
