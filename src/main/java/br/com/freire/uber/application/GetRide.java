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
        var passengerOptional = accountDAO.getAccountById(ride.getPassengerId().toString());
        var passenger = passengerOptional.orElseThrow(() -> new ValidationError("Ride without passenger", -9));
        return new OutputGetRide(
                ride.getRideId().toString(),
                ride.getPassengerId().toString(),
                ride.getFromLat(),
                ride.getFromLong(),
                ride.getToLat(),
                ride.getToLong(),
                ride.getStatus(),
                passenger.getName(),
                passenger.getEmail()
                );
    }

    public record InputGetRide(String rideId) {
    }

    public record OutputGetRide(String rideId, String passengerId, BigDecimal fromLat, BigDecimal fromLong,
                                BigDecimal toLat, BigDecimal toLong, String status, String passengerName, String passengerEmail) {
    }
}
