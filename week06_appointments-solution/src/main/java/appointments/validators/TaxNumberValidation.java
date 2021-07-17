package appointments.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = TaxNumberValidator.class)
public @interface TaxNumberValidation {

    String message() default "Invalid tax code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int numberLength() default 10;

}
