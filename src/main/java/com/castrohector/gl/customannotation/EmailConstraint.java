package com.castrohector.gl.customannotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraint {

    // Java 8
    String message() default "Error al validar Email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

