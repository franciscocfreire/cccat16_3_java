package br.com.freire.uber;

import br.com.freire.uber.domain.ValidateCpf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateCpfTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "97456321558",
            "71428793860",
            "87748248800"
    })
    void deveTestarCpfValido(String cpf) {
        assertTrue(ValidateCpf.validate(cpf));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "11111111111",
            "123",
            "1234566789123456789"
    })
    void deveTestarCpfInvalido(String cpf) {
        assertFalse(ValidateCpf.validate(cpf));
    }
}
