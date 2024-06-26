package br.com.freire.uber.application;

import br.com.freire.uber.resource.AccountDAO;
import br.com.freire.uber.resource.RideDAO;

import java.math.BigDecimal;

public class GetRide {

    AccountDAO accountDAO;
    RideDAO rideDAO;

    public GetRide(AccountDAO accountDAO, RideDAO rideDAO) {
        this.accountDAO = accountDAO;
        this.rideDAO = rideDAO;
    }

    public OutputGetRide execute(InputGetRide input) {
        var rideOptional = rideDAO.getRideById(input.rideId);
        var ride = rideOptional.orElseThrow(() -> new ValidationError("Ride not found", -8));
        return new OutputGetRide(
                ride.getRideId().toString(),
                ride.getPassagerId().toString(),
                ride.getFromLat(),
                ride.getFromLong(),
                ride.getToLat(),
                ride.getToLong(),
                ride.getStatus());
    }

    public record InputGetRide(String rideId) {
    }

    public record OutputGetRide(String rideId, String passagerId, BigDecimal fromLat, BigDecimal fromLong,
                                BigDecimal toLat, BigDecimal toLong, String status) {
    }
}
