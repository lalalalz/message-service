package kr.co.kwt.messageapi.adapter.out.messaging.kafka;

import kr.co.kwt.messageapi.application.port.out.MessageProducerPort;
import kr.co.kwt.messageapi.common.exception.MessageSendException;
import kr.co.kwt.messageapi.domain.message.Channel;
import kr.co.kwt.messageapi.domain.message.Message;
import kr.co.kwt.messageapi.domain.message.Type;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageProducerPort implements MessageProducerPort {
    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final Environment environment;

    @Value("${kafka.topic.informational.email}")
    private String informationalEmailTopic;

    @Value("${kafka.topic.informational.push}")
    private String informationalPushTopic;

    @Value("${kafka.topic.advertising.email}")
    private String advertisingEmailTopic;

    @Value("${kafka.topic.advertising.push}")
    private String advertisingPushTopic;

    @Override
    public void send(Message message) {
        String topic = getTopicByMessageTypeAndPurpose(message.getType().name(), message.getChannel().name());

        try {
            kafkaTemplate
                    .send(topic, message.getTo().getIdentity(), message)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Message sent successfully. Topic: {}, Message: {}", topic, message);
                        }
                        else {
                            log.error("Failed to send message. Topic: {}, Message: {}, Error: {}",
                                    topic, message, ex.getMessage(), ex);
                        }
                    });
        } catch (Exception e) {
            log.error("Error occurred while sending message. Topic: {}, Message: {}, Error: {}",
                    topic, message, e.getMessage(), e);
            throw new MessageSendException("Failed to send message", e);
        }
    }

    private String getTopicByMessageTypeAndPurpose(String type, String purpose) {
        Type messageType = Type.valueOf(type.toUpperCase());
        Channel channel = Channel.valueOf(purpose.toUpperCase());

        return switch (messageType) {
            case INFORMATIONAL -> switch (channel) {
                case EMAIL -> informationalEmailTopic;
                case PUSH -> informationalPushTopic;
                default -> throw new IllegalStateException("Unexpected value: " + channel);
            };
            case ADVERTISING -> switch (channel) {
                case EMAIL -> advertisingEmailTopic;
                case PUSH -> advertisingPushTopic;
                default -> throw new IllegalStateException("Unexpected value: " + channel);
            };
        };
    }
}