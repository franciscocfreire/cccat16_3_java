package br.com.freire.uber.application;

import br.com.freire.uber.resource.Resource;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetAccount {
    private final Resource resource;

    public GetAccount(Resource resource) {
        this.resource = resource;
    }

    public Account getAccount(UUID accountId) {
        return resource.getAccountById(accountId).map(result -> Account.restore(
                ((UUID) result.get("account_id")),
                (String) result.get("name"),
                (String) result.get("email"),
                (String) result.get("cpf"),
                (String) result.get("car_plate"),
                (Boolean) result.get("is_passenger"),
                (Boolean) result.get("is_driver")))
                .orElse(null);
    }
}
