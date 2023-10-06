package com.igorfood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class MultiploValidador implements ConstraintValidator<Multiplo,Number> {

    private int numero;
    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numero = constraintAnnotation.numero();
    }

    @Override
    public boolean isValid(Number valor, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;
        if (valor != null){
            BigDecimal valorDecimal = BigDecimal.valueOf(valor.doubleValue());
            BigDecimal multiploDecimal = BigDecimal.valueOf(valor.doubleValue());
            BigDecimal resto = valorDecimal.remainder(multiploDecimal);
            valido = BigDecimal.ZERO.compareTo(resto) == 0;
            return valido;
        }

        return valido;
    }
}
