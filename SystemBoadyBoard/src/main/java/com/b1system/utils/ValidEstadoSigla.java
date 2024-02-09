package com.b1system.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = EstadoSiglaValidator.class)
public @interface ValidEstadoSigla {
    String message() default "Estado não válido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}