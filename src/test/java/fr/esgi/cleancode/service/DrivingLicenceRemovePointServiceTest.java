package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DrivingLicenceRemovePointServiceTest {

    @InjectMocks
    private DrivingLicenceRemovePointService service;

    @Mock
    private InMemoryDatabase database;

    @Mock
    private DrivingLicenceFinderService drivingLicenceFinderService;

    @Mock
    private final UUID drivingLicenceId = UUID.randomUUID();
    private DrivingLicence expectedDrivingLicence;

    @BeforeEach
    public void expectedDrivingLicence() {
        expectedDrivingLicence = DrivingLicence.builder()
                .id(drivingLicenceId)
                .driverSocialSecurityNumber("012345678910111")
                .build();
    }


    @Test
    void shouldThrowWhenDrivingLicenceNotFound() throws ResourceNotFoundException {
        when(database.findById(drivingLicenceId)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
            service.removePoints(drivingLicenceId, 6);
        });
    }

    @Test
    void shouldRemoveGivenPointsToDrivingLicence() {
        when(database.findById(drivingLicenceId)).thenReturn(Optional.ofNullable(expectedDrivingLicence));
        DrivingLicence currentDrivingLicence = service.removePoints(drivingLicenceId, 6);
        Assertions.assertEquals(expectedDrivingLicence.getAvailablePoints(), currentDrivingLicence.getAvailablePoints());
    }

}
