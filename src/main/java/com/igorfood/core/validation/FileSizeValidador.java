package com.igorfood.core.validation;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileSizeValidador implements ConstraintValidator<FileSize, MultipartFile> {
    private DataSize max;
    @Override
    public void initialize(FileSize constraint) {
        this.max = DataSize.parse(constraint.max());
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext constraintValidatorContext) {
        return value == null || value.getSize() <= this.max.toBytes() ;
    }
}
