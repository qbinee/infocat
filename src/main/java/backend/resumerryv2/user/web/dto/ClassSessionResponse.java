package backend.resumerryv2.user.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassSessionResponse {
    private Long classSessionId;
    private String title;
    private String name;
    private String status;
    private String applyDate;
    private String bookingDay;
    private Character duration;
}
