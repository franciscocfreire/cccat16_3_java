package br.com.freire.uber.infrastructure.repository;

import br.com.freire.uber.domain.Account;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountRepositoryDatabase implements AccountRepository {

    private final JdbcTemplate jdbcTemplate;

    public AccountRepositoryDatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Account> getAccountByEmail(String email) {
        String sql = "SELECT * FROM cccat16.account WHERE email = ?";
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, email);
            Account account = convertMapToAccount(result);
            return Optional.of(account);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Account> getAccountById(UUID accountId) {
        String sql = "SELECT * FROM cccat16.account WHERE account_id = ?";
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, accountId);
            Account account = convertMapToAccount(result);
            return Optional.of(account);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public UUID saveAccount(Account account) {
        jdbcTemplate.update("INSERT INTO cccat16.account (account_id, name, email, cpf, car_plate, is_passenger, is_driver) VALUES (?, ?, ?, ?, ?, ?, ?)",
                account.getAccountId(), account.getName(), account.getEmail(), account.getCpf(), account.getCarPlate(), account.isPassenger(), account.isDriver());

        return account.getAccountId();
    }

    private Account convertMapToAccount(Map<String, Object> result) {
        if (result == null) return null;
        return Account.restore(
                ((UUID) result.get("account_id")),
                (String) result.get("name"),
                (String) result.get("email"),
                (String) result.get("cpf"),
                (String) result.get("car_plate"),
                (Boolean) result.get("is_passenger"),
                (Boolean) result.get("is_driver")
        );
    }


}
