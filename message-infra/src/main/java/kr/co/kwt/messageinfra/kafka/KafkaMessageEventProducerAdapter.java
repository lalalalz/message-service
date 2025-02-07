package kr.co.kwt.messageinfra.kafka;

import kr.co.kwt.messagecore.application.port.out.MessageEventProducerPort;
import kr.co.kwt.messagecore.application.port.out.SaveMessageEvent;
import kr.co.kwt.messagecore.domain.Channel;
import kr.co.kwt.messagecore.domain.Type;
import kr.co.kwt.messageinfra.common.MessageSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageEventProducerAdapter implements MessageEventProducerPort {

    @Value("${kafka.topic.informational.email}")
    private String informationalEmailTopic;

    @Value("${kafka.topic.informational.push}")
    private String informationalPushTopic;

    @Value("${kafka.topic.advertising.email}")
    private String advertisingEmailTopic;

    @Value("${kafka.topic.advertising.push}")
    private String advertisingPushTopic;

    private final KafkaTemplate<UUID, SaveMessageEvent> kafkaTemplate;

    @Override
    public void send(SaveMessageEvent saveMessageEvent) {
        String topic = getTopicByMessageTypeAndPurpose(saveMessageEvent.getType(), saveMessageEvent.getChannel());

        try {
            kafkaTemplate
                    .send(topic, saveMessageEvent.getId(), saveMessageEvent)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Message sent successfully. Topic: {}, MessageId: {}",
                                    topic, saveMessageEvent.getId());
                        }
                        else {
                            log.error("Failed to send message. Topic: {}, MessageId: {}, Error: {}",
                                    topic, saveMessageEvent.getId(), ex.getMessage(), ex);
                        }
                    });
        }
        catch (Exception e) {
            log.error("Error occurred while sending message. Topic: {}, MessageId: {}, Error: {}",
                    topic, saveMessageEvent.getId(), e.getMessage(), e);
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
            };
            case ADVERTISING -> switch (channel) {
                case EMAIL -> advertisingEmailTopic;
                case PUSH -> advertisingPushTopic;
            };
        };
    }
}