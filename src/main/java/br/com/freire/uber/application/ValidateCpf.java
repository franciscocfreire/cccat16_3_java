package br.com.freire.uber.application;

public class ValidateCpf {
    public static  boolean validateCpf(String cpf) {
        return CpfValidator.validate(cpf);
    }
}
