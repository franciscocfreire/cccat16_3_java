package br.com.freire.uber.application;

import br.com.freire.uber.resource.AccountDAO;
import br.com.freire.uber.resource.RideRepository;

import java.math.BigDecimal;

public class GetRide {

    AccountDAO accountDAO;
    RideRepository rideRepository;

    public GetRide(AccountDAO accountDAO, RideRepository rideRepository) {
        this.accountDAO = accountDAO;
        this.rideRepository = rideRepository;
    }

    public OutputGetRide execute(InputGetRide input) {
        var rideOptional = rideRepository.getRideById(input.rideId);
        var ride = rideOptional.orElseThrow(() -> new ValidationError("Ride not found", -8));
        var passengerOptional = accountDAO.getAccountById(ride.getPassengerId());
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
