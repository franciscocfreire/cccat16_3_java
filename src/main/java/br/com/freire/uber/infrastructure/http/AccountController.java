package br.com.freire.uber.infrastructure.http;

import br.com.freire.uber.domain.Account;
import br.com.freire.uber.application.usecase.GetAccount;
import br.com.freire.uber.application.usecase.Signup;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AccountController {

    @Autowired
    GetAccount getAccount;
    @Autowired
    Signup signup;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            SignupResponse signupResponse = signup.execute(objectMapper.convertValue(request, new TypeReference<>() {
            }));
            return ResponseEntity.ok(signupResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), -1);
            return ResponseEntity.unprocessableEntity().body(errorResponse);
        }
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<?> getAccount(@PathVariable String accountId) {
        Account account = getAccount.getAccount(UUID.fromString(accountId));
        if (account != null) return ResponseEntity.ok(account);
        return ResponseEntity.notFound().build();
    }


}
