package kr.co.kwt.messageinfra.adapter.event.kafka.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "kafka")
@ConditionalOnProperty(prefix = "kafka", name = "enabled", havingValue = "true", matchIfMissing = true)
@Component
public class KafkaProperties {

    public static final String APPLICATION_PACKAGE_PATH = "kr.co.kwt.*";

    private String bootstrapServers;
    private Consumer consumer = new Consumer();
    private Producer producer = new Producer();
    private Security security = new Security();
    private Sasl sasl = new Sasl();
    private Factory factory = new Factory();

    @Getter
    @Setter
    public static class Factory {
        private Integer concurrency;
    }

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
    public static class Producer {
        private String ack;
        private Integer retries;
        private Integer batchSize;
        private Integer lingerMs;
        private Integer timeout;
        private Integer bufferMemory;
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
