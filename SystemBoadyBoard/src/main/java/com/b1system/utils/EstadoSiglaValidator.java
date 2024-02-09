package com.b1system.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EstadoSiglaValidator implements ConstraintValidator<ValidEstadoSigla, String> {

    @Override
    public boolean isValid(String sigla, ConstraintValidatorContext context) {
        if (sigla == null || sigla.isEmpty()) {
            return false;
        }
        try {
            UnidadeFederacao.fromSigla(sigla);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
