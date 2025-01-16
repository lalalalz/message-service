package kr.co.kwt.messageapi.adapter.out.messaging.kafka;

import kr.co.kwt.messageapi.domain.model.Message;
import kr.co.kwt.messageapi.domain.model.MessagePurpose;
import kr.co.kwt.messageapi.domain.model.MessageType;
import kr.co.kwt.messageapi.application.port.out.MessageProducerPort;
import kr.co.kwt.messageapi.common.exception.MessageSendException;
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
        String topic = getTopicByMessageTypeAndPurpose(message.getType(), message.getPurpose());

        try {
            kafkaTemplate.send(topic, message.getTo(), message)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Message sent successfully. Topic: {}, Message: {}", topic, message);
                        } else {
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
        MessageType messageType = MessageType.valueOf(type.toUpperCase());
        MessagePurpose messagePurpose = MessagePurpose.valueOf(purpose.toUpperCase());

        return switch (messagePurpose) {
            case INFORMATIONAL -> switch (messageType) {
                case EMAIL -> informationalEmailTopic;
                case PUSH -> informationalPushTopic;
            };
            case ADVERTISING -> switch (messageType) {
                case EMAIL -> advertisingEmailTopic;
                case PUSH -> advertisingPushTopic;
            };
        };
    }
}