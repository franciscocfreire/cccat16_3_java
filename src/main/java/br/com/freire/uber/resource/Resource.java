package br.com.freire.uber.resource;

import br.com.freire.uber.application.Account;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class Resource {
    private final AccountDAO accountDAO;

    public Resource(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Optional<Map<String, Object>> getAccountByEmail(String email) {
        return accountDAO.getAccountByEmail(email);
    }

    public Optional<Map<String, Object>> getAccountById(String accountId) {
        return accountDAO.getAccountById(accountId);
    }

    public String saveAccount(Account account) {
        return accountDAO.saveAccount(account);
    }
}
