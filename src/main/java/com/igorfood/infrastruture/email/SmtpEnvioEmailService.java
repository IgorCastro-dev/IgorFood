package com.igorfood.infrastruture.email;

import com.igorfood.core.email.EmailProperties;
import com.igorfood.domain.services.EnvioEmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SmtpEnvioEmailService implements EnvioEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void enviar(Menssagem menssagem) {
        try {
            MimeMessage mimeMessage = criarMimeMessage(menssagem);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Não foi possivel enviar o email",e);
        }

    }

    protected MimeMessage criarMimeMessage(Menssagem menssagem) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String corpo = processarTemplate(menssagem);
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setFrom(emailProperties.getRemetente());
        helper.setTo(menssagem.getDestinatarios().toArray(new String[0]));
        helper.setText(corpo,true);
        helper.setSubject(menssagem.getAssunto());
        return mimeMessage;
    }

    protected String processarTemplate(Menssagem menssagem) {
        try {
            Template template = freemarkerConfig.getTemplate(menssagem.getCorpo());
            return FreeMarkerTemplateUtils.processTemplateIntoString(template,menssagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Não foi possivel fazer o template",e);
        }
    }
}
