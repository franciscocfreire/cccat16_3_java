package br.com.freire.uber.application;

import br.com.freire.uber.resource.AccountDAO;
import br.com.freire.uber.resource.RideRepository;

import java.math.BigDecimal;
import java.util.UUID;

public class ResquestRide {

    AccountDAO accountDAO;
    RideRepository rideRepository;

    public ResquestRide(AccountDAO accountDAO, RideRepository rideRepository) {
        this.accountDAO = accountDAO;
        this.rideRepository = rideRepository;
    }

    public OutputRequestRide execute(InputRequestRide input) {

        var optionalAccount = accountDAO.getAccountById(UUID.fromString(input.passengerId));
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
