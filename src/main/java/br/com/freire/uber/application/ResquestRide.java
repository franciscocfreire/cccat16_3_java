package br.com.freire.uber.application;

import br.com.freire.uber.resource.AccountDAO;
import br.com.freire.uber.resource.RideDAO;

public class ResquestRide {

    AccountDAO accountDAO;

    public ResquestRide(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
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
        return null;
    }

    public record InputRequestRide(String passagerId, double fromLat, double fromLong, double toLat, double toLong){};
    public record OutputRequestRide(String rideId){};
}
