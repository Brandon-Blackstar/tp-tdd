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

import javax.swing.text.html.Option;
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
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> {
            service.removePoints(UUID.randomUUID(), 6);
        });
    }

    @Test
    void shouldNotRemovePointsWhenUnderZero() throws IllegalArgumentException {
        when(drivingLicenceFinderService.findById(drivingLicenceId)).thenReturn(Optional.ofNullable(expectedDrivingLicence));
        IllegalArgumentException illegalArgumentException = Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
            service.removePoints(drivingLicenceId, expectedDrivingLicence.getAvailablePoints()+1);
        });
        Assertions.assertEquals("You can't have less than 0 points on your driving licence !", illegalArgumentException.getMessage());
    }


    @Test
    void shouldRemoveGivenPointsToDrivingLicence() {
        when(drivingLicenceFinderService.findById(drivingLicenceId)).thenReturn(Optional.ofNullable(expectedDrivingLicence));
        expectedDrivingLicence = expectedDrivingLicence.withAvailablePoints(6);
        when(database.save(drivingLicenceId, expectedDrivingLicence)).thenReturn(expectedDrivingLicence.withAvailablePoints(6));
        DrivingLicence currentDrivingLicence = service.removePoints(drivingLicenceId, 6);
        Assertions.assertEquals(expectedDrivingLicence.getAvailablePoints(), currentDrivingLicence.getAvailablePoints());
    }

}
