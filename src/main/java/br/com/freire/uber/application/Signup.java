package br.com.freire.uber.application;

import br.com.freire.uber.driver.SignupRequest;
import br.com.freire.uber.driver.SignupResponse;
import br.com.freire.uber.resource.MailerGateway;
import br.com.freire.uber.resource.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class Signup {

    private final Resource resource;
    private final MailerGateway mailerGateway;
    private final ObjectMapper objectMapper;


    public Signup(Resource resource, MailerGateway mailerGateway) {
        this.resource = resource;
        this.mailerGateway = mailerGateway;
        this.objectMapper = new ObjectMapper();
    }

    public SignupResponse execute(Map<String, String> request) {
        SignupRequest input = objectMapper.convertValue(request, SignupRequest.class);
        var existingAccount = resource.getAccountByEmail(input.getEmail());
        if (existingAccount.isPresent()) throw new ValidationError("Account already exist", -4);
        Account account = Account.create(input.getName(), input.getEmail(), input.getCpf(), input.getCarPlate(), input.isPassenger(), input.isDriver());
        UUID accountId = resource.saveAccount(account);
        mailerGateway.send(input.getEmail(), "Welcome", "");
        return new SignupResponse(accountId.toString());
    }
}
