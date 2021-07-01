package locations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CoordinateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Coordinate {
    String message() default "Invalid coordinate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Type type() default Type.LAT;
}
