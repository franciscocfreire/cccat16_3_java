package br.com.freire.uber;

import br.com.freire.uber.infrastructure.repository.AccountRepository;
import br.com.freire.uber.infrastructure.repository.AccountRepositoryMemory;
import br.com.freire.uber.infrastructure.gateway.MailerGateway;
import br.com.freire.uber.infrastructure.gateway.MailerGatewayMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public MailerGateway mailerGateway(){
        return new MailerGatewayMemory();
    }

    @Bean
    public AccountRepository accountRepository(){
        return new AccountRepositoryMemory();
    }
}
