package br.com.freire.uber.resource;

import br.com.freire.uber.application.Ride;

import java.util.Optional;
import java.util.UUID;

public interface RideRepository {
    void saveRide(Ride ride);

    Optional<Ride> getRideById(String rideId);
    boolean hasActiveRideByPassengerId(UUID passengerId);
}
