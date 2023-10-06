package com.igorfood.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class ValorZeroIncluiDescricaoValidador implements ConstraintValidator<ValorZeroIncluiDescricao,Object> {
   private String valorField;
   private String descricaoField;
   private String descricaoObrigatoriaField;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraint) {
        this.valorField = constraint.valorField();
        this.descricaoField = constraint.descricaoField();
        this.descricaoObrigatoriaField = constraint.descricaoObrigatoriaField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        boolean validation = true;
        try {
            BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(object.getClass(),valorField)
                    .getReadMethod().invoke(object);

            String descricao = (String) BeanUtils.getPropertyDescriptor(object.getClass(),descricaoField).getReadMethod().invoke(object);

            if (BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                validation = descricao.toLowerCase().contains(this.descricaoObrigatoriaField.toLowerCase());
            }
            return validation;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
