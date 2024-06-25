package br.com.freire.uber.resource;

import br.com.freire.uber.application.Account;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class Resource {
    private final AccountDAO accountDAO;

    public Resource(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Optional<Map<String, Object>> getAccountByEmail(String email) {
        return accountDAO.getAccountByEmail(email)
                .map(this::convertAccountToMap);
    }

    public Optional<Map<String, Object>> getAccountById(String accountId) {
        return accountDAO.getAccountById(accountId)
                .map(this::convertAccountToMap);
    }

    public String saveAccount(Account account) {
        return accountDAO.saveAccount(account);
    }

    private Map<String, Object> convertAccountToMap(Account account) {
        if (account == null) return null;
        Map<String, Object> accountMap = new HashMap<>();
        accountMap.put("account_id", UUID.fromString(account.getAccountId()));
        accountMap.put("name", account.getName());
        accountMap.put("email", account.getEmail());
        accountMap.put("cpf", account.getCpf());
        accountMap.put("car_plate", account.getCarPlate());
        accountMap.put("is_passenger", account.isPassenger());
        accountMap.put("is_driver", account.isDriver());

        return accountMap;
    }
}
