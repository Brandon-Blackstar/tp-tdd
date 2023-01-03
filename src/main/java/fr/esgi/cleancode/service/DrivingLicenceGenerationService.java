package fr.esgi.cleancode.service;


import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import fr.esgi.cleancode.model.DrivingLicence;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrivingLicenceGenerationService {

    private final InMemoryDatabase database = InMemoryDatabase.getInstance();

    public void validateDriverSocialSecurityNumber(String driverSocialSecurityNumber) throws InvalidDriverSocialSecurityNumberException {
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

    public DrivingLicence createNewDrivingLicence(String driverSocialSecurityNumber) {
        this.validateDriverSocialSecurityNumber(driverSocialSecurityNumber);
        UUID drivingLicenceId = new DrivingLicenceIdGenerationService().generateNewDrivingLicenceId();
        DrivingLicence drivingLicence = DrivingLicence.builder()
                .id(drivingLicenceId)
                .driverSocialSecurityNumber(driverSocialSecurityNumber)
                .build();
        return database.save(drivingLicenceId, drivingLicence);
    }
}
