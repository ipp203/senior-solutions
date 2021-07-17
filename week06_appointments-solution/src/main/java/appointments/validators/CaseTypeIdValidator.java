package appointments.validators;

import appointments.NavService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CaseTypeIdValidator implements ConstraintValidator<CaseTypeIdValidation,String> {
    @Autowired
    private NavService service;

    @Override
    public boolean isValid(String id, ConstraintValidatorContext constraintValidatorContext) {
        return service.getTypes().stream()
                .anyMatch(t->t.getId().equals(id));
    }

    @Override
    public void initialize(CaseTypeIdValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
