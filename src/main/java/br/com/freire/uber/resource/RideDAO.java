package br.com.freire.uber.resource;

import br.com.freire.uber.application.Ride;

import java.util.Optional;

public interface RideDAO {
    void saveRide(Ride ride);

    Optional<Ride> getRideById(String rideId);
    //boolean hasActiveRideByPassengerId(String passengerId);
}
