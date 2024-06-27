package br.com.freire.uber.application.usecase;

import br.com.freire.uber.domain.Ride;
import br.com.freire.uber.domain.exceptions.ValidationError;
import br.com.freire.uber.infrastructure.repository.AccountRepository;
import br.com.freire.uber.infrastructure.repository.RideRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class ResquestRide {

    AccountRepository accountRepository;
    RideRepository rideRepository;

    public ResquestRide(AccountRepository accountRepository, RideRepository rideRepository) {
        this.accountRepository = accountRepository;
        this.rideRepository = rideRepository;
    }

    public OutputRequestRide execute(InputRequestRide input) {

        var optionalAccount = accountRepository.getAccountById(UUID.fromString(input.passengerId));
        if (optionalAccount.isEmpty()) {
            throw new ValidationError("Account not found", -5);
        }

        var account = optionalAccount.get();
        if (!account.isPassenger()) {
            throw new ValidationError("Account is not from passenger", -6);
        }
        var hasActiveRide = rideRepository.hasActiveRideByPassengerId(account.getAccountId());
        if (hasActiveRide) throw new ValidationError("Passenger has an active ride", -7);
        Ride ride = Ride.create(
                UUID.fromString(input.passengerId),
                input.fromLat,
                input.fromLong,
                input.toLat,
                input.toLong
        );
        rideRepository.saveRide(ride);

        return new OutputRequestRide(ride.getRideId().toString());
    }

    public record InputRequestRide(String passengerId, BigDecimal fromLat, BigDecimal fromLong, BigDecimal toLat,
                                   BigDecimal toLong) {
    }

    public record OutputRequestRide(String rideId) {
    }
}
