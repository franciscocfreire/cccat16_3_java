package br.com.freire.uber.infrastructure.http;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String name;
    private String email;
    private String cpf;
    private String carPlate;
    private boolean isPassenger;
    private boolean isDriver;
}