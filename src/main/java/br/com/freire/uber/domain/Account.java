package br.com.freire.uber.domain;

import br.com.freire.uber.domain.exceptions.ValidationError;
import lombok.Getter;

import java.util.UUID;
import java.util.regex.Pattern;

@Getter
public class Account {
    private final UUID accountId;
    private final String name;
    private final String email;
    private final String cpf;
    private final String carPlate;
    private final boolean isPassenger;
    private final boolean isDriver;

    private Account(UUID accountId, String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver) {
        this.accountId = accountId;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.carPlate = carPlate;
        this.isPassenger = isPassenger;
        this.isDriver = isDriver;
        if (!(Pattern.matches("[a-zA-Z]+ [a-zA-Z]+", this.getName()))) throw new ValidationError("Invalid name", -3);
        if (!Pattern.matches("^(.+)@(.+)$", this.getEmail())) throw new ValidationError("Invalid email", -2);
        if (!ValidateCpf.validate(this.getCpf())) throw new ValidationError("Invalid CPF", -1);
        if (this.isDriver() && !this.getCarPlate().isEmpty() && !Pattern.matches("[A-Z]{3}[0-9]{4}", this.getCarPlate()))
            throw new ValidationError("Invalid car plate", -5);
    }

    public static Account create(String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver) {
        return new Account(UUID.randomUUID(), name, email, cpf, carPlate, isPassenger, isDriver);
    }

    public static Account restore(UUID accountId, String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver) {
        return new Account(accountId, name, email, cpf, carPlate, isPassenger, isDriver);
    }

}