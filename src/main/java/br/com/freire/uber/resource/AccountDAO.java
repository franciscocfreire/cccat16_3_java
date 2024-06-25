package br.com.freire.uber.resource;

import br.com.freire.uber.application.Account;

import java.util.Map;
import java.util.Optional;

public interface AccountDAO {
    Optional<Account> getAccountByEmail(String email);

    Optional<Account> getAccountById(String accountId);

    String saveAccount(Account account);
}
