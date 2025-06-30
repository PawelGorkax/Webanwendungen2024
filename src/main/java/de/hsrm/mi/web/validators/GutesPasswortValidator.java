package de.hsrm.mi.web.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GutesPasswortValidator implements ConstraintValidator<GutesPasswort, String> {

protected int teiler;

@Override
public void initialize(GutesPasswort annotationTeilbar) {
}
@Override
public boolean isValid(String obj, ConstraintValidatorContext ctx) {
    if(obj == null){
        return true;
    }
    return obj.toLowerCase().contains("17") || obj.toLowerCase().contains("siebzehn");
} }
