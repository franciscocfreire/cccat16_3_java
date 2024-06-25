package br.com.freire.uber;

import br.com.freire.uber.application.ResquestRide;
import br.com.freire.uber.application.Signup;
import br.com.freire.uber.application.ValidationError;
import br.com.freire.uber.driver.SignupRequest;
import br.com.freire.uber.driver.SignupResponse;
import br.com.freire.uber.resource.AccountDAO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RequestRideTest {

    @Autowired
    Signup signup;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AccountDAO accountDAO;



/*    @Test
    @DisplayName("Deve solicitar uma corrida")
    public void solicitarUmaCorrida() {
        //Given
        String expectedName = "John Doe";
        String expectedEmail = "john.doe" + Math.random() + "@gmail.com";
        String expectedCpf = "87748248800";
        SignupRequest request = new SignupRequest();
        request.setName(expectedName);
        request.setEmail(expectedEmail);
        request.setCpf(expectedCpf);
        request.setPassenger(true);
        request.setDriver(false);
        SignupResponse responseSignup = signup.execute(objectMapper.convertValue(request, new TypeReference<>() {
        }));
        var requestRide = new ResquestRide(accountDAO, rideDAO);
        ResquestRide.InputRequestRide inputRequestRide = new ResquestRide.InputRequestRide(
                responseSignup.getAccountId(),
        -27.584905257808835,
        -48.545022195325124,
        -27.496887588317275,
        -48.522234807851476);
        var outputRequestRide = requestRide.execute(inputRequestRide);
        assertNotNull(outputRequestRide.rideId());
        var getRide = new GetRide(accountDAO, rideDAO);
        var inputGetRide = new InputGetRide();
        inputGetRide.setRideId(outputRequestRide.rideId);
        //When
        var outputGetRide = getRide.execute(inputGetRide);
        //Then
        assertEquals("requested", outputGetRide.status);
        assertEquals(expectedName, outputGetRide.passagerName);
        assertEquals(expectedEmail, outputGetRide.email);
    }*/

    @Test
    @DisplayName("Não deve poder solicitar uma corrida senão for um passageiro")
    public void naoDeveSolicitarCorridaSenaoPassageiro() {
        //Given
        String expectedName = "John Doe";
        String expectedEmail = "john.doe" + Math.random() + "@gmail.com";
        String expectedCpf = "87748248800";
        int expectedError = -6;
        SignupRequest request = new SignupRequest();
        request.setName(expectedName);
        request.setEmail(expectedEmail);
        request.setCpf(expectedCpf);
        request.setPassenger(false);
        request.setDriver(true);
        request.setCarPlate("AAA9999");
        SignupResponse responseSignup = signup.execute(objectMapper.convertValue(request, new TypeReference<>() {
        }));
        var requestRide = new ResquestRide(accountDAO);
        ResquestRide.InputRequestRide inputRequestRide = new ResquestRide.InputRequestRide(
                responseSignup.getAccountId(),
                -27.584905257808835,
                -48.545022195325124,
                -27.496887588317275,
                -48.522234807851476);

        ValidationError validationError = assertThrows(ValidationError.class, () -> {
            requestRide.execute(objectMapper.convertValue(inputRequestRide, new TypeReference<>() {
            }));
        });

        assertEquals(expectedError, validationError.getErrorCode());



    }
}
