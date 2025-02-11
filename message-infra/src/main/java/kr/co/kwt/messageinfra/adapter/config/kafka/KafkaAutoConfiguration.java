package kr.co.kwt.messageinfra.adapter.config.kafka;

import jakarta.annotation.PostConstruct;
import kr.co.kwt.messagecore.application.port.out.event.MessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.Assert;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnClass(KafkaTemplate.class)
@ConditionalOnProperty(prefix = "kafka", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaAutoConfiguration {

    private final KafkaProperties kafkaProperties;

    @PostConstruct
    public void initialize() {
        Assert.notNull(kafkaProperties.getBootstrapServers(), "bootstrapServers must not be null");
        Assert.notNull(kafkaProperties.getConsumer().getGroupId(), "consumerGroupId must not be null");
        Assert.notNull(kafkaProperties.getSecurity().getProtocol(), "security protocol must not be null");
        Assert.notNull(kafkaProperties.getSasl().getMechanism(), "SASL mechanism must not be null");

        log.info("Kafka configuration initialized with bootstrap servers: {}", kafkaProperties.getBootstrapServers());
        log.info("Consumer group ID: {}", kafkaProperties.getConsumer().getGroupId());
    }

    @Bean
    @ConditionalOnMissingBean
    public ConsumerFactory<String, MessageEvent> consumerFactory() {
        Map<String, Object> props = new HashMap<>();

        // 메시지 변환 처리
        JsonDeserializer<MessageEvent> deserializer = new JsonDeserializer<>(MessageEvent.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(true);
        deserializer.addTrustedPackages(KafkaProperties.APPLICATION_PACKAGE_PATH);

        // 필수 설정
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        // 성능 설정
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kafkaProperties.getConsumer().getAutoOffsetReset());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaProperties.getConsumer().getEnableAutoCommit());
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaProperties.getConsumer().getAutoCommitInterval());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaProperties.getConsumer().getMaxPollRecords());
        props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, kafkaProperties.getConsumer().getFetchMinBytes());
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, kafkaProperties.getConsumer().getFetchMaxWait());
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, kafkaProperties.getConsumer().getHeartbeatInterval());
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kafkaProperties.getConsumer().getSessionTimeout());
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, kafkaProperties.getConsumer().getMaxPollInterval());

        // 보안 설정
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSecurity().getProtocol());
        props.put(SaslConfigs.SASL_MECHANISM, kafkaProperties.getSasl().getMechanism());
        props.put(SaslConfigs.SASL_JAAS_CONFIG, kafkaProperties.getSasl().getJaas().getConfig());

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultErrorHandler defaultErrorHandler() {
        // 기본 에러 핸들러
        return new DefaultErrorHandler(
                ((consumerRecord, e) -> {
                    log.error("Failed to process record after all retries. Exception: {}, Record: {}",
                            e, consumerRecord);
                }), new FixedBackOff(1000L, 3));
    }

    @Bean
    @ConditionalOnMissingBean
    public ConcurrentKafkaListenerContainerFactory<String, MessageEvent> kafkaListenerContainerFactory(
            ConsumerFactory<String, MessageEvent> consumerFactory,
            DefaultErrorHandler errorhandler
    ) {
        ConcurrentKafkaListenerContainerFactory<String, MessageEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(kafkaProperties.getFactory().getConcurrency());
        factory.setCommonErrorHandler(errorhandler);

        return factory;
    }

    @Bean
    @ConditionalOnMissingBean
    public ProducerFactory<String, MessageEvent> producerFactory() {
        Map<String, Object> props = new HashMap<>();

        // Producer 기본 설정
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Producer 성능 설정
//        props.put(ProducerConfig.ACKS_CONFIG, kafkaProperties.getProducer().getAck());
//        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProperties.getProducer().getRetries());
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProperties.getProducer().getBatchSize());
//        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProperties.getProducer().getLingerMs());
//        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, kafkaProperties.getProducer().getBufferMemory());

        // 보안 설정 (이 부분은 유지)
        props.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, kafkaProperties.getSecurity().getProtocol());
        props.put(SaslConfigs.SASL_MECHANISM, kafkaProperties.getSasl().getMechanism());
        props.put(SaslConfigs.SASL_JAAS_CONFIG, kafkaProperties.getSasl().getJaas().getConfig());

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    @ConditionalOnMissingBean
    public KafkaTemplate<String, MessageEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}


