package br.com.freire.uber;

import br.com.freire.uber.application.usecase.GetAccount;
import br.com.freire.uber.application.usecase.Signup;
import br.com.freire.uber.domain.Account;
import br.com.freire.uber.infrastructure.http.SignupRequest;
import br.com.freire.uber.infrastructure.http.SignupResponse;
import br.com.freire.uber.infrastructure.gateway.MailerGateway;
import br.com.freire.uber.infrastructure.repository.AccountRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationMockTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    MailerGateway mailerGateway;

    @Test
    @DisplayName("Deve criar uma conta para o passageiro com stub")
    void deveCriarContaParaPassageiroComStub() {
        String expectedName = "John Doe";
        String expectedEmail = "john.doe" + Math.random() + "@gmail.com";
        String expectedCpf = "87748248800";
        String expectedCarPlate = "XYZ-1234";
        boolean expectedPassenger = true;
        boolean expectedDriver = false;

        SignupRequest request = new SignupRequest();
        request.setName(expectedName);
        request.setEmail(expectedEmail);
        request.setCpf(expectedCpf);
        request.setPassenger(true);
        request.setDriver(false);
        Account mockResult = Account.create(expectedName, expectedEmail, expectedCpf, expectedCarPlate, expectedPassenger, expectedDriver);

        when(accountRepository.getAccountById(any(UUID.class))).thenReturn(Optional.of(mockResult));
        when(accountRepository.saveAccount(any(Account.class))).thenReturn(UUID.randomUUID());
        Signup signup = new Signup(accountRepository, mailerGateway);

        ObjectMapper objectMapper = new ObjectMapper();
        SignupResponse responseSignup = signup.execute(objectMapper.convertValue(request, new TypeReference<>() {
        }));

        assertNotNull(responseSignup);
        assertNotNull(responseSignup.getAccountId());

        GetAccount getAccount = new GetAccount(accountRepository);

        Account account = getAccount.getAccount(UUID.fromString(responseSignup.getAccountId()));
        assertEquals(expectedName, account.getName());
        assertEquals(expectedEmail, account.getEmail());
        assertEquals(expectedCpf, account.getCpf());
    }
}
