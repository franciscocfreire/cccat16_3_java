package br.com.freire.uber.application.usecase;

import br.com.freire.uber.domain.exceptions.ValidationError;
import br.com.freire.uber.infrastructure.repository.AccountRepository;
import br.com.freire.uber.infrastructure.repository.RideRepository;

import java.math.BigDecimal;

public class GetRide {

    AccountRepository accountRepository;
    RideRepository rideRepository;

    public GetRide(AccountRepository accountRepository, RideRepository rideRepository) {
        this.accountRepository = accountRepository;
        this.rideRepository = rideRepository;
    }

    public OutputGetRide execute(InputGetRide input) {
        var rideOptional = rideRepository.getRideById(input.rideId);
        var ride = rideOptional.orElseThrow(() -> new ValidationError("Ride not found", -8));
        var passengerOptional = accountRepository.getAccountById(ride.getPassengerId());
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
