package br.com.freire.uber;

import br.com.freire.uber.resource.AccountRepository;
import br.com.freire.uber.resource.AccountRepositoryMemory;
import br.com.freire.uber.resource.MailerGateway;
import br.com.freire.uber.resource.MailerGatewayMemory;
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
