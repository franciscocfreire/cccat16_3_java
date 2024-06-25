package br.com.freire.uber.resource;

import br.com.freire.uber.application.Account;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountDAODatabase implements AccountDAO {

    private final JdbcTemplate jdbcTemplate;

    public AccountDAODatabase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Map<String, Object>> getAccountByEmail(String email) {
        String sql = "SELECT * FROM cccat16.account WHERE email = ?";
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, email);
            return Optional.of(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Map<String, Object>> getAccountById(String accountId) {
        String sql = "SELECT * FROM cccat16.account WHERE account_id = ?";
        try {
            Map<String, Object> result = jdbcTemplate.queryForMap(sql, UUID.fromString(accountId));
            return Optional.of(result);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public String saveAccount(Account account) {
        jdbcTemplate.update("INSERT INTO cccat16.account (account_id, name, email, cpf, car_plate, is_passenger, is_driver) VALUES (?, ?, ?, ?, ?, ?, ?)",
                UUID.fromString(account.getAccountId()), account.getName(), account.getEmail(), account.getCpf(), account.getCarPlate(), account.isPassenger(), account.isDriver());

        return account.getAccountId();
    }
}
