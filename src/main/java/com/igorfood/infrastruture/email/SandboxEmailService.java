package com.igorfood.infrastruture.email;

import com.igorfood.core.email.EmailProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Set;

public class SandboxEmailService extends SmtpEnvioEmailService {
    @Value("${igorfood.sandbox.destinatario}")
    private String sandboxEmail;
    @Autowired
    private EmailProperties emailProperties;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    protected MimeMessage criarMimeMessage(Menssagem menssagem) throws MessagingException {
        MimeMessage mimeMessage = super.criarMimeMessage(menssagem);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"UTF-8");
        helper.setTo(sandboxEmail);
        return mimeMessage;
    }

}
