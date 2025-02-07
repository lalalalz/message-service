package kr.co.kwt.messageinfra.kafka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "kafka")
@Component
public class KafkaProperties {

    private String bootstrapServers;
    private Consumer consumer = new Consumer();
    private Security security = new Security();
    private Sasl sasl = new Sasl();

    @Getter
    @Setter
    public static class Consumer {
        private String groupId;
        private String autoOffsetReset;
        private Boolean enableAutoCommit;
        private Integer autoCommitInterval;
        private Integer maxPollRecords;
        private Integer fetchMinBytes;
        private Integer fetchMaxWait;
        private Integer heartbeatInterval;
        private Integer sessionTimeout;
        private Integer maxPollInterval;
    }

    @Getter
    @Setter
    public static class Security {
        private String protocol;
    }

    @Getter
    @Setter
    public static class Sasl {
        private String mechanism;
        private Jaas jaas = new Jaas();

        @Getter
        @Setter
        public static class Jaas {
            private String config;
        }
    }
}
