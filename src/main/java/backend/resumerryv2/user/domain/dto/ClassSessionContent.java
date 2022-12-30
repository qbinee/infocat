package backend.resumerryv2.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
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
