package fr.esgi.cleancode.service;

import org.junit.jupiter.api.Test;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class DrivingLicenceGenerationServiceTest {
    private final DrivingLicenceGenerationService service = new DrivingLicenceGenerationService();

    @Test
    void should_generate_valid_driverSocialSecurityNumber() throws InvalidDriverSocialSecurityNumberException {
        try{
            service.validateDriverSocialSecurityNumber(null);
            fail("Nothing thrown");
        }catch(InvalidDriverSocialSecurityNumberException e){
            assertThat(e.getMessage()).isEqualTo("Invalid Social Security Number is null");
        }

        try{
            service.validateDriverSocialSecurityNumber("4568sd");
            fail("Nothing thrown");
        }catch(InvalidDriverSocialSecurityNumberException e){
            assertThat(e.getMessage()).isEqualTo("Invalid Social Security Number contains letter");
        }

        try{
            service.validateDriverSocialSecurityNumber("45685874562548");
            fail("Nothing thrown");
        }catch(InvalidDriverSocialSecurityNumberException e){
            assertThat(e.getMessage()).isEqualTo("Invalid Social Security Number size");
        }

        try{
            service.validateDriverSocialSecurityNumber("45685874562548457");
            fail("Nothing thrown");
        }catch(InvalidDriverSocialSecurityNumberException e){
            assertThat(e.getMessage()).isEqualTo("Invalid Social Security Number size");
        }

        try{
            service.validateDriverSocialSecurityNumber("45685874562548457");
            fail("Nothing thrown");
        }catch(InvalidDriverSocialSecurityNumberException e){
            assertThat(e.getMessage()).isEqualTo("Invalid Social Security Number size");
        }

    }

}