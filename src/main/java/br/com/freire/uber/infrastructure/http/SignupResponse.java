package br.com.freire.uber.infrastructure.http;

import lombok.Getter;

@Getter
public class SignupResponse {
    private String accountId;
    private int errorCode;

    public SignupResponse() {
    }

    public SignupResponse(String accountId) {
        this.accountId = accountId;
    }

    public SignupResponse(int errorCode) {
        this.errorCode = errorCode;
    }
}