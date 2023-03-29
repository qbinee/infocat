/* Licensed under InfoCat */
package backend.resumerryv2.exception.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({
    Default.class,
    ValidationGroups.NotEmptyGroup.class,
    ValidationGroups.PatternCheckGroup.class
})
public interface ValidationSequence {}
