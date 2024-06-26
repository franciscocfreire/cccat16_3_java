package br.com.freire.uber.application;

import br.com.freire.uber.resource.AccountDAO;
import br.com.freire.uber.resource.RideDAO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ResquestRide {

    AccountDAO accountDAO;
    RideDAO rideDAO;

    public ResquestRide(AccountDAO accountDAO, RideDAO rideDAO) {
        this.accountDAO = accountDAO;
        this.rideDAO = rideDAO;
    }

    public OutputRequestRide execute(InputRequestRide input){

        var optionalAccount = accountDAO.getAccountById(input.passagerId);
        if (optionalAccount.isEmpty()) {
            throw new ValidationError("Account not found", -5);
        }

        var account = optionalAccount.get();
        if (!account.isPassenger()) {
            throw new ValidationError("Account is not from passenger", -6);
        }
        /*var hasActiveRide = rideDAO.hasActiveRideByPassagerId(account.getAccountId());
        if(hasActiveRide) throw new ValidationError("Passager has an active ride", -7);*/
        Ride ride = new Ride();
        ride.setRideId(UUID.randomUUID());
        ride.setStatus("requested");
        ride.setDate(LocalDateTime.now());
        ride.setFromLat(input.fromLat);
        ride.setFromLong(input.fromLong);
        ride.setToLat(input.toLat);
        ride.setToLong(input.toLong);
        ride.setPassagerId(UUID.fromString(input.passagerId));
        rideDAO.saveRide(ride);

        return new OutputRequestRide(ride.getRideId().toString());
    }

    public record InputRequestRide(String passagerId, BigDecimal fromLat, BigDecimal fromLong, BigDecimal toLat, BigDecimal toLong){}
    public record OutputRequestRide(String rideId){}
}
