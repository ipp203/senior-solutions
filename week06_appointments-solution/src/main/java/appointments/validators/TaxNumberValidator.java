package appointments.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TaxNumberValidator implements ConstraintValidator<TaxNumberValidation, String> {

    private int numberLength;

    @Override
    public void initialize(TaxNumberValidation constraintAnnotation) {
        numberLength = constraintAnnotation.numberLength();
    }

    @Override
    public boolean isValid(String taxNumber, ConstraintValidatorContext constraintValidatorContext) {
        return TaxNumberValidatorPojo.check(taxNumber,numberLength);
    }
}
