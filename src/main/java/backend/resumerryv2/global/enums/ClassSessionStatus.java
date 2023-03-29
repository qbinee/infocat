/* Licensed under InfoCat */
package backend.resumerryv2.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClassSessionStatus {
    PENDING("Pending"),
    ASSIGN("Assign"),
    MENTOR_REFUSE("MentorRefuse"),
    USER_REFUSE("UserReFuse"),
    EXPIRED("ExpiredDate"),
    COMPLETE("Complete");
    private String name;
}
