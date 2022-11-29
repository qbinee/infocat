package backend.resumerryv2.exception.validation;

import backend.resumerryv2.mentor.web.annotation.validator.SortedValidator;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, ValidationGroups.NotEmptyGroup.class, ValidationGroups.PatternCheckGroup.class, SortedValidator.class})
public interface ValidationSequence {
}
