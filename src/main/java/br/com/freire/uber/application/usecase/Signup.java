package br.com.freire.uber.application.usecase;

import br.com.freire.uber.domain.Account;
import br.com.freire.uber.domain.exceptions.ValidationError;
import br.com.freire.uber.infrastructure.http.SignupRequest;
import br.com.freire.uber.infrastructure.http.SignupResponse;
import br.com.freire.uber.infrastructure.gateway.MailerGateway;
import br.com.freire.uber.infrastructure.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class Signup {

    private final AccountRepository accountRepository;
    private final MailerGateway mailerGateway;
    private final ObjectMapper objectMapper;


    public Signup(AccountRepository accountRepository, MailerGateway mailerGateway) {
        this.accountRepository = accountRepository;
        this.mailerGateway = mailerGateway;
        this.objectMapper = new ObjectMapper();
    }

    public SignupResponse execute(Map<String, String> request) {
        SignupRequest input = objectMapper.convertValue(request, SignupRequest.class);
        var existingAccount = accountRepository.getAccountByEmail(input.getEmail());
        if (existingAccount.isPresent()) throw new ValidationError("Account already exist", -4);
        Account account = Account.create(input.getName(), input.getEmail(), input.getCpf(), input.getCarPlate(), input.isPassenger(), input.isDriver());
        UUID accountId = accountRepository.saveAccount(account);
        mailerGateway.send(input.getEmail(), "Welcome", "");
        return new SignupResponse(accountId.toString());
    }
}
