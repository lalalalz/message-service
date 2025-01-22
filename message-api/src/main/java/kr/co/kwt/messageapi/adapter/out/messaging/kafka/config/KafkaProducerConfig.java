package kr.co.kwt.messageapi.adapter.out.messaging.kafka.config;

import kr.co.kwt.messageapi.domain.message.Message;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value(value = "${kafka.compression-type}")
    private String compressionType;

    @Value(value = "${kafka.linger-ms}")
    private String lingerMs;

    @Value(value = "${kafka.acks}")
    private String acks;

    @Value(value = "${kafka.retries}")
    private String retries;

    @Value(value = "${kafka.security.protocol}")
    private String securityProtocol;

    @Value(value = "${kafka.sasl.mechanism}")
    private String saslMechanism;

    @Value(value = "${kafka.sasl.jaas.config}")
    private String saslJaasConfig;

    @Bean
    public ProducerFactory<String, Message> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType);
        props.put(ProducerConfig.LINGER_MS_CONFIG, lingerMs);
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, securityProtocol);
        props.put(SaslConfigs.SASL_MECHANISM, saslMechanism);
        props.put(SaslConfigs.SASL_JAAS_CONFIG, saslJaasConfig);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, Message> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}