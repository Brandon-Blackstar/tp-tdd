package fr.esgi.cleancode.service;


import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrivingLicenceGenerationService {

    public void validateDriverSocialSecurityNumber(String driverSocialSecurityNumber) {
        if (driverSocialSecurityNumber == null) {
            throw new InvalidDriverSocialSecurityNumberException("Invalid Social Security Number is null");
        }
        Pattern patternIsAllNumbers = Pattern.compile("[0-9]+");
        Pattern patternIsOk = Pattern.compile("[0-9]{15}");
        Matcher matcherIsAllNumbers = patternIsAllNumbers.matcher(driverSocialSecurityNumber);
        Matcher matcherIsOk = patternIsOk.matcher(driverSocialSecurityNumber);
        if (!matcherIsAllNumbers.matches()) {
            throw new InvalidDriverSocialSecurityNumberException("Invalid Social Security Number contains letter");
        }
        if (!matcherIsOk.matches()) {
            throw new InvalidDriverSocialSecurityNumberException("Invalid Social Security Number size");
        }
    }

}
