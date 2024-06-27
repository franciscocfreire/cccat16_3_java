package br.com.freire.uber.application.usecase;

import br.com.freire.uber.domain.Account;
import br.com.freire.uber.infrastructure.repository.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GetAccount {
    private final AccountRepository accountRepository;

    public GetAccount(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccount(UUID accountId) {
        return accountRepository.getAccountById(accountId).map(result -> Account.restore(
                result.getAccountId(),
                result.getName(),
                result.getEmail(),
                result.getCpf(),
                result.getCarPlate(),
                result.isPassenger(),
                result.isDriver()))
                .orElse(null);
    }
}
