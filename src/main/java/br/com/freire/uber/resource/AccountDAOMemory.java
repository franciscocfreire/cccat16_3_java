package br.com.freire.uber.resource;

import br.com.freire.uber.application.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class AccountDAOMemory implements AccountDAO {

    Map<UUID, Account> mapAccountByAccountId;
    Map<String, Account> mapAccountByAccountEmail;

    public AccountDAOMemory() {
        this.mapAccountByAccountId = new HashMap<>();
        this.mapAccountByAccountEmail = new HashMap<>();
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        return Optional.ofNullable(this.mapAccountByAccountEmail.get(email));
    }

    @Override
    public Optional<Account> getAccountById(UUID accountId) {
        return Optional.ofNullable(this.mapAccountByAccountId.get(accountId));
    }

    @Override
    public UUID saveAccount(Account account) {
        this.mapAccountByAccountId.put(account.getAccountId(), account);
        this.mapAccountByAccountEmail.put(account.getEmail(), account);
        return account.getAccountId();
    }
}
