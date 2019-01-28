package com.example.projects.rest.validation.validators;

import com.example.projects.rest.validation.validators.annotations.DateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidator implements ConstraintValidator<DateFormat, String> {

    private String format;

    public void initialize(DateFormat constraintAnnotation) {
        this.format = constraintAnnotation.value();
    }

    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return true;
        }

        java.text.DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        }
        catch (ParseException e) {
            return false;
        }
    }
}
