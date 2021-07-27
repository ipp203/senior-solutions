package bank.model.transaction;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AccountNumberValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidAccountNumber {
    String message() default "The account number must be exactly 8 long!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    int length() default 8;
}
