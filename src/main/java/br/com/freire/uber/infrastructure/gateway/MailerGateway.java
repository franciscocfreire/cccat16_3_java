package br.com.freire.uber.infrastructure.gateway;

public interface MailerGateway {

    void send (String recipient, String subject, String content);
}
