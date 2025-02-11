package kr.co.kwt.messageinfra.adapter.event.kafka;

import kr.co.kwt.messagecore.application.port.out.PublishMessageEventPort;
import kr.co.kwt.messagecore.application.port.out.event.MessageEvent;
import kr.co.kwt.messagecore.domain.Channel;
import kr.co.kwt.messagecore.domain.Type;
import kr.co.kwt.messageinfra.common.MessageSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPublishMessageEventAdapter implements PublishMessageEventPort {

    @Value("${kafka.topic.informational.email}")
    private String informationalEmailTopic;

    @Value("${kafka.topic.informational.push}")
    private String informationalPushTopic;

    @Value("${kafka.topic.advertising.email}")
    private String advertisingEmailTopic;

    @Value("${kafka.topic.advertising.push}")
    private String advertisingPushTopic;

    private final KafkaTemplate<String, MessageEvent> kafkaTemplate;

    @Override
    public void publish(MessageEvent messageEvent) {
        String topic = getTopicByMessageTypeAndPurpose(messageEvent.getType(), messageEvent.getChannel());

        try {
            kafkaTemplate
                    .send(topic, messageEvent.getId().toString(), messageEvent)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Message sent successfully. Topic: {}, MessageId: {}",
                                    topic, messageEvent.getId());
                        }
                        else {
                            log.error("Failed to send message. Topic: {}, MessageId: {}, Error: {}",
                                    topic, messageEvent.getId(), ex.getMessage(), ex);
                        }
                    });
        }
        catch (Exception e) {
            log.error("Error occurred while sending message. Topic: {}, MessageId: {}, Error: {}",
                    topic, messageEvent.getId(), e.getMessage(), e);
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