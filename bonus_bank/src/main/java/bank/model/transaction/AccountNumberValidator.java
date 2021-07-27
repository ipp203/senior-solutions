package bank.model.transaction;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AccountNumberValidator implements ConstraintValidator<ValidAccountNumber, String> {

    private int length;

    public void initialize(ValidAccountNumber constraint) {
        length=constraint.length();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.length()==length;
    }
}
