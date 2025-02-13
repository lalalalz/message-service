package kr.co.kwt.messageagent.config.email;

import co.kr.kwt.starter.kms.modulekms.service.KmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties(EmailProperties.class)
public class EmailConfig {

    private final KmsService kmsService;

    public EmailConfig(KmsService kmsService) {
        this.kmsService = kmsService;
    }

    @PostConstruct
    public void init() throws JsonProcessingException {
        KmsSecret secrets = kmsService.getSecrets(KmsSecret.class);
        secrets = kmsService.getSecrets(KmsSecret.class);
        String secretKey = secrets.getSecretKey();
    }

    @Bean
    public JavaMailSender javaMailSender(EmailProperties emailProperties) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(emailProperties.getHost());
        mailSender.setPort(emailProperties.getPort());
        mailSender.setUsername(emailProperties.getUsername());
        mailSender.setPassword(emailProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put(EmailProperties.MAIL_TRANSPORT_PROTOCOL, emailProperties.getTransportProtocol());
        props.put(EmailProperties.MAIL_SMTP_AUTH, emailProperties.getSmtp().isAuth());
        props.put(EmailProperties.MAIL_SMTP_STARTTLS_ENABLE, emailProperties.getSmtp().getStarttls().isEnable());
        props.put(EmailProperties.MAIL_SMTP_SSL_ENABLE, emailProperties.getSmtp().getSsl().isEnable());
        props.put(EmailProperties.MAIL_SMTP_SSL_TRUST, emailProperties.getSmtp().getSsl().getTrust());
        props.put(EmailProperties.MAIL_DEBUG, emailProperties.isDebug());

        return mailSender;
    }
}
