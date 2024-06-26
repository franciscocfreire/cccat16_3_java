package br.com.freire.uber.resource;

import br.com.freire.uber.application.Ride;

public interface RideDAO {
    void saveRide(Ride ride);
    //boolean hasActiveRideByPassagerId(String passagerId);
}
