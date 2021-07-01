package locations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;


public class CoordinateValidator implements ConstraintValidator<Coordinate,Double> {

    private Type type;

    @Override
    public void initialize(Coordinate constraintAnnotation) {
        type = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(Double aDouble, ConstraintValidatorContext constraintValidatorContext) {
        if(type == Type.LAT) {
            return aDouble>-90 && aDouble<90;
        }
        if(type == Type.LON){
            return aDouble>-180 && aDouble<180;
        }
        return false;
    }
}
