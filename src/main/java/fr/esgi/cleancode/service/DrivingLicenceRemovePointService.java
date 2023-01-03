package fr.esgi.cleancode.service;

import fr.esgi.cleancode.database.InMemoryDatabase;
import fr.esgi.cleancode.exception.ResourceNotFoundException;
import fr.esgi.cleancode.model.DrivingLicence;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class DrivingLicenceRemovePointService {

    private final InMemoryDatabase database;
    private final DrivingLicenceFinderService drivingLicenceFinderService;

    public DrivingLicence removePoints(UUID drivingLicenceId, int points) throws ResourceNotFoundException, IllegalArgumentException {
        Optional<DrivingLicence> drivingLicence = drivingLicenceFinderService.findById(drivingLicenceId);

        if(drivingLicence.isEmpty()) {
            throw new ResourceNotFoundException("Driving licence not found");
        }

        DrivingLicence newDrivingLicence = drivingLicence.get();
        final int newPoints = newDrivingLicence.getAvailablePoints() - points;
        if (newPoints < 0) {
            throw new IllegalArgumentException("You can't have less than 0 points on your driving licence !");
        }

        newDrivingLicence = DrivingLicence.builder()
                .id(newDrivingLicence.getId())
                .driverSocialSecurityNumber(newDrivingLicence.getDriverSocialSecurityNumber())
                .availablePoints(newPoints)
                .build();

        return database.save(drivingLicenceId, newDrivingLicence);
    }
}
