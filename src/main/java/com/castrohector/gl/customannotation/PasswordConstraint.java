package com.castrohector.gl.customannotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {

    // Java 8
    String message() default "Error al validar Password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

