package fr.esgi.cleancode.service;

import org.junit.jupiter.api.Test;
import fr.esgi.cleancode.exception.InvalidDriverSocialSecurityNumberException;
import org.mockito.Mock;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class DrivingLicenceGenerationServiceTest {
    @Mock
    private final DrivingLicenceGenerationService service = new DrivingLicenceGenerationService();

    @Test
    void shouldGenerateValidDriverSocialSecurityNumber() throws InvalidDriverSocialSecurityNumberException {
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

    @Test
    void shouldGenerateNewDrivingLicenceWithDefaultValues() {
        final var drivingLicence = service.createNewDrivingLicence("198064562548457");
        assertThat(drivingLicence.getAvailablePoints()).isEqualTo(12);
    }


}