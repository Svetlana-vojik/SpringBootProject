package by.teachmeskills.springbootproject.utils;

import by.teachmeskills.springbootproject.exceptions.RequestParamNullException;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class ValidatorUtil {

    private ValidatorUtil() {
    }

    private static final String REQUEST_PARAMETER_IS_NULL_ERROR = "Request parameter is not initialized!";

    public static boolean validateUserData(Map<String, String> data) {
        boolean validName = false, validSurname = false, validEmail = false,
                validBirthday = false, validAddress = false;
        if (data.get("name") != null) {
            validName = data.get("name").matches("[A-Za-z А-Яа-я]+")
                    && Character.isUpperCase((data.get("name")).charAt(0));
        }
        if (data.get("surname") != null) {
            validSurname = data.get("surname").matches("[A-Za-z А-Яа-я]+")
                    && Character.isUpperCase((data.get("surname")).charAt(0));
        }
        if (data.get("email") != null) {
            validEmail = (data.get("email")).matches("^(.+)@(\\S+)$");
        }
        if (data.get("birthday") != null) {
            validBirthday = (data.get("birthday")).matches("\\d{4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])*");
        }
        if (data.get("address") != null) {
            validAddress = (data.get("address")).matches("[A-Za-z А-Яа-я0-9\\d]+");
        }
        return validName && validSurname && validEmail && validBirthday && validAddress;
    }

    public static void validateParamNotNull(String... parameters) throws RequestParamNullException {
        if (Arrays.stream(parameters).anyMatch(Objects::isNull)) {
            throw new RequestParamNullException(REQUEST_PARAMETER_IS_NULL_ERROR);
        }
    }
}