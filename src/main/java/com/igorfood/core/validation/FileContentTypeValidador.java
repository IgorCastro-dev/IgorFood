package com.igorfood.core.validation;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.List;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public class FileContentTypeValidador implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowed;

    @Override
    public void initialize(FileContentType constraint) {
        this.allowed = List.of(constraint.allowed());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile == null || this.allowed.contains(multipartFile.getContentType());
    }
}
