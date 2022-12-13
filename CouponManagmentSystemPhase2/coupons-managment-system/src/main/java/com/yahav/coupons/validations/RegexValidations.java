package com.yahav.coupons.validations;

import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexValidations {

    public static boolean passwordValidation(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean emailValidation(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean nameValidation(String name) {
        String regex = "^[A-Za-z0-9-_.\\s]{2,30}+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean phoneValidation(String phone) {
        String regex = "^[\\+]?[(]?[0-9]{2,4}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean codeValidation(String code) {
        String regex = "^[A-Z0-9]{2,16}+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);
        return matcher.matches();
    }

    public static boolean imageFileValidation(String image) {
        String regex = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(image);
        return matcher.matches();
    }
}
