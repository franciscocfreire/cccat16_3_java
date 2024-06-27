package br.com.freire.uber.infrastructure.repository;

import br.com.freire.uber.domain.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {
    Optional<Account> getAccountByEmail(String email);

    Optional<Account> getAccountById(UUID accountId);

    UUID saveAccount(Account account);
}
