package br.com.freire.uber.infrastructure.repository;

import br.com.freire.uber.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class Resource {
    private final AccountRepository accountRepository;

    public Resource(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Map<String, Object>> getAccountByEmail(String email) {
        return accountRepository.getAccountByEmail(email)
                .map(this::convertAccountToMap);
    }

    public Optional<Map<String, Object>> getAccountById(UUID accountId) {
        return accountRepository.getAccountById(accountId)
                .map(this::convertAccountToMap);
    }

    public UUID saveAccount(Account account) {
        return accountRepository.saveAccount(account);
    }

    private Map<String, Object> convertAccountToMap(Account account) {
        if (account == null) return null;
        Map<String, Object> accountMap = new HashMap<>();
        accountMap.put("account_id", account.getAccountId());
        accountMap.put("name", account.getName());
        accountMap.put("email", account.getEmail());
        accountMap.put("cpf", account.getCpf());
        accountMap.put("car_plate", account.getCarPlate());
        accountMap.put("is_passenger", account.isPassenger());
        accountMap.put("is_driver", account.isDriver());

        return accountMap;
    }
}
