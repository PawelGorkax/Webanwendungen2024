package de.hsrm.mi.web.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD }) 
@Retention(RetentionPolicy.RUNTIME) 
@Constraint(validatedBy=GutesPasswortValidator.class)
@Documented 
public @interface GutesPasswort {
String message() default "Muss “17” oder das Wort “siebzehn” enthalten";

Class<? extends Payload>[] payload() default { };

Class<?>[] groups() default { };

}
