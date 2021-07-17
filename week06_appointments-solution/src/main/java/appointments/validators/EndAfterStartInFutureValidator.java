package appointments.validators;

import appointments.Interval;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class EndAfterStartInFutureValidator implements ConstraintValidator<EndAfterStartInFuture, Interval> {
    @Override
    public boolean isValid(Interval interval, ConstraintValidatorContext constraintValidatorContext) {
        return interval.getStart().isBefore(interval.getEnd()) &&
               interval.getStart().isAfter(LocalDateTime.now()) &&
               interval.getEnd().isAfter(LocalDateTime.now()) ;
    }

    @Override
    public void initialize(EndAfterStartInFuture constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
