package kr.co.kwt.messageagent.config.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "email")
public class EmailProperties {

    public static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";
    public static final String MAIL_SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
    public static final String MAIL_DEBUG = "mail.debug";

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String transportProtocol;
    private boolean debug;
    private Smtp smtp = new Smtp();

    @Getter
    @Setter
    public static class Smtp {
        private boolean auth;
        private Starttls starttls = new Starttls();
        private Ssl ssl = new Ssl();

        @Getter
        @Setter
        public static class Starttls {
            boolean enable;
        }

        @Getter
        @Setter
        public static class Ssl {
            boolean enable;
            String trust;
        }
    }
}
