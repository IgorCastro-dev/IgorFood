package com.igorfood.core.email;

import com.igorfood.domain.services.EnvioEmailService;
import com.igorfood.infrastruture.email.FakeEnvioEmailService;
import com.igorfood.infrastruture.email.SandboxEmailService;
import com.igorfood.infrastruture.email.SmtpEnvioEmailService;
import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;


    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // Configura o SendGrid como o provedor SMTP
        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(emailProperties.getPort());
        mailSender.setUsername(emailProperties.getUsename());
        mailSender.setPassword(emailProperties.getPassword());

        return mailSender;
    }

    @Bean
    public SendGrid sendGrid() {
        return new SendGrid(emailProperties.getPassword());
    }

    @Bean
    public EnvioEmailService envioEmailService() {
        // Acho melhor usar switch aqui do que if/else if
        switch (emailProperties.getImpl()) {
            case FAKE:
                return new FakeEnvioEmailService();
            case SMTP:
                return new SmtpEnvioEmailService();
            case SANDBOX:
                return new SandboxEmailService();
            default:
                return null;
        }
    }
}
