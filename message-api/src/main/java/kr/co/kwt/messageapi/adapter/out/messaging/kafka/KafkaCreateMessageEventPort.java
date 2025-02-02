package kr.co.kwt.messageapi.adapter.out.messaging.kafka;

import kr.co.kwt.messageapi.application.port.out.CreateMessageEvent;
import kr.co.kwt.messageapi.application.port.out.CreateMessageEventPort;
import kr.co.kwt.messageapi.common.exception.MessageSendException;
import kr.co.kwt.messageapi.domain.message.Channel;
import kr.co.kwt.messageapi.domain.message.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaCreateMessageEventPort implements CreateMessageEventPort {

    private final KafkaTemplate<UUID, CreateMessageEvent> kafkaTemplate;

    @Value("${kafka.topic.informational.email}")
    private String informationalEmailTopic;

    @Value("${kafka.topic.informational.push}")
    private String informationalPushTopic;

    @Value("${kafka.topic.advertising.email}")
    private String advertisingEmailTopic;

    @Value("${kafka.topic.advertising.push}")
    private String advertisingPushTopic;

    @Override
    public void publish(CreateMessageEvent createMessageEvent) {
        String topic = getTopicByMessageTypeAndPurpose(
                Type.valueOf(createMessageEvent.getType()),
                Channel.valueOf(createMessageEvent.getChannel()));

        try {
            kafkaTemplate
                    .send(topic, createMessageEvent.getId(), createMessageEvent)
                    .thenAccept(doSuccessLogging())
                    .exceptionally(doFailureLogging(createMessageEvent, topic));
        }
        catch (Exception e) {
            log.error("Error occurred while sending message. Topic: {}, Message: {}, Error: {}",
                    topic, createMessageEvent, e.getMessage(), e);
            throw new MessageSendException("Failed to send message", e);
        }
    }

    private Consumer<SendResult<UUID, CreateMessageEvent>> doSuccessLogging() {
        return (result) -> log.info("Message sent successfully. Topic: {}, Key: {}, Message: {}",
                result.getProducerRecord().topic(),
                result.getProducerRecord().key(),
                result.getProducerRecord().value());
    }

    private Function<Throwable, Void> doFailureLogging(CreateMessageEvent createMessageEvent, String topic) {
        return (ex) -> {
            log.error("Failed to send message. Topic: {}, Message: {}, Error: {}",
                    topic, createMessageEvent, ex.getMessage(), ex);
            return null;
        };
    }

    private String getTopicByMessageTypeAndPurpose(Type type, Channel channel) {
        return switch (type) {
            case INFORMATIONAL -> switch (channel) {
                case EMAIL -> informationalEmailTopic;
                case PUSH -> informationalPushTopic;
            };
            case ADVERTISING -> switch (channel) {
                case EMAIL -> advertisingEmailTopic;
                case PUSH -> advertisingPushTopic;
            };
        };
    }
}