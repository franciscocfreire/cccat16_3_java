package br.com.freire.uber.application;

import lombok.Getter;

import java.util.UUID;

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
    }

    public static Account create(String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver) {
        return new Account(UUID.randomUUID(), name, email, cpf, carPlate, isPassenger, isDriver);
    }

    public static Account restore(UUID accountId, String name, String email, String cpf, String carPlate, boolean isPassenger, boolean isDriver) {
        return new Account(accountId, name, email, cpf, carPlate, isPassenger, isDriver);
    }

}