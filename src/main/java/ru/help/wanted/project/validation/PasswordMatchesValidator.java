package ru.help.wanted.project.validation;

import ru.help.wanted.project.model.dto.AppUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        AppUserDto user = (AppUserDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
