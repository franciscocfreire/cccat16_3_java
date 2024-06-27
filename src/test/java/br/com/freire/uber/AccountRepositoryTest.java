package br.com.freire.uber;

import br.com.freire.uber.domain.Account;
import br.com.freire.uber.infrastructure.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("Deve salvar um registro na tabela account e consultar por id")
    void deveSalvarUmRegistroNaTabelaAccountEConsultarPorId() {
        String expectedName = "John Doe";
        String expectedEmail = "john.doe" + Math.random() + "@gmail.com";
        String expectedCpf = "87748248800";

        Account account = Account.create(expectedName, expectedEmail,expectedCpf, null,true, false );

        UUID accountId = accountRepository.saveAccount(account);

        Optional<Account> optionalSavedAccountById = accountRepository.getAccountById(accountId);

        assertTrue(optionalSavedAccountById.isPresent(), "Esperado que a conta salva esteja presente");

        Account savedAccountById = optionalSavedAccountById.get();

        assertEquals(expectedName, savedAccountById.getName());
        assertEquals(expectedEmail, savedAccountById.getEmail());
        assertEquals(expectedCpf, savedAccountById.getCpf());
    }

    @Test
    @DisplayName("Deve salvar um registro na tabela account e consultar por id")
    void deveSalvarUmRegistroNaTabelaAccountEConsultarPorEmail() {
        String expectedName = "John Doe";
        String expectedEmail = "john.doe" + Math.random() + "@gmail.com";
        String expectedCpf = "87748248800";
        String expectedCarPlate = null;
        boolean expectedIsPassenger = true;
        boolean expectedIsDriver = false;

        Account account = Account.create(expectedName, expectedEmail, expectedCpf, expectedCarPlate, expectedIsPassenger, expectedIsDriver);
        accountRepository.saveAccount(account);

        Optional<Account> optionalSavedAccountByEmail = accountRepository.getAccountByEmail(expectedEmail);

        assertTrue(optionalSavedAccountByEmail.isPresent(), "Esperado que a conta salva esteja presente");

        Account savedAccountByEmail = optionalSavedAccountByEmail.get();

        assertEquals(expectedName, savedAccountByEmail.getName());
        assertEquals(expectedEmail, savedAccountByEmail.getEmail());
        assertEquals(expectedCpf, savedAccountByEmail.getCpf());
    }


}