package com.castrohector.gl.customannotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint password) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext cvc) {
        if (password != null) {
            String regex = "^(?=[^A-Z]*[A-Z][^A-Z]*$).{8,12}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(password);

            if (matcher.matches()) {
                Long n = "0123456789".chars().filter(ch -> password.indexOf(ch) != -1).count();

                return n == 2L;
            } else {
                return false;
            }

        }
        return false;
    }

}

