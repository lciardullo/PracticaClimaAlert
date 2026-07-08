package ar.edu.utn.frba.domain.adapter.helper;

import ar.edu.utn.frba.config.EnvProvider;
import ar.edu.utn.frba.domain.notificacion.Notificacion;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class SpringMailHelper {

    private JavaMailSenderImpl mailSender;

    public void init() {
        if (mailSender != null) {
            return;
        }

        mailSender = new JavaMailSenderImpl();
        mailSender.setHost(EnvProvider.getRequired("MAIL_HOST"));
        mailSender.setPort(Integer.parseInt(EnvProvider.getRequired("MAIL_PORT")));
        mailSender.setUsername(EnvProvider.getRequired("MAIL_USERNAME"));
        mailSender.setPassword(EnvProvider.getRequired("MAIL_PASSWORD"));
        mailSender.setProtocol("smtp");
        mailSender.setDefaultEncoding("UTF-8");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", getOrDefault("MAIL_SMTP_AUTH", "true"));
        properties.put("mail.smtp.starttls.enable", getOrDefault("MAIL_SMTP_STARTTLS_ENABLE", "true"));
        properties.put("mail.smtp.starttls.required", getOrDefault("MAIL_SMTP_STARTTLS_REQUIRED", "true"));
    }

    public SimpleMailMessage createMailMessage(Notificacion notificacion) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificacion.getMedio().getValor());
        message.setFrom(getFromAddress());
        message.setSubject(getSubject());
        message.setText(notificacion.getMensaje());
        return message;
    }

    public void send(SimpleMailMessage message) {
        init();
        mailSender.send(message);
    }

    private String getFromAddress() {
        String from = EnvProvider.get("MAIL_FROM");
        if (from != null && !from.isBlank()) {
            return from;
        }
        return EnvProvider.getRequired("MAIL_USERNAME");
    }

    private String getSubject() {
        return getOrDefault("MAIL_SUBJECT", "Notificacion");
    }

    private String getOrDefault(String key, String defaultValue) {
        String value = EnvProvider.get(key);
        if (value != null && !value.isBlank()) {
            return value;
        }
        return defaultValue;
    }
}
