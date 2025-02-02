package kr.co.kwt.messageagent.kafka.event;

import kr.co.kwt.messageagent.agent.EmailAgent;
import kr.co.kwt.messageagent.agent.PushAgent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateEventMessageConsumer {

    private final PushAgent pushAgent;
    private final EmailAgent emailAgent;

    @KafkaListener(topics = "${kafka.topic.informational.email}")
    public void consumeInformationalEmail(ConsumerRecord<UUID, CreateMessageEvent> record) {
        emailAgent.sendInformationalEmail(record.value());
    }

    @KafkaListener(topics = "${kafka.topic.advertising.email}")
    public void consumeAdvertisingEmail(ConsumerRecord<UUID, CreateMessageEvent> record) {
        emailAgent.sendAdvertisingEmail(record.value());
    }

    @KafkaListener(topics = "${kafka.topic.informational.email}")
    public void consumeInformationalPush(ConsumerRecord<UUID, CreateMessageEvent> record) {
        pushAgent.sendInformationalPush(record.value());
    }

    @KafkaListener(topics = "${kafka.topic.advertising.push}")
    public void consumeAdvertisingPush(ConsumerRecord<UUID, CreateMessageEvent> record) {
        pushAgent.sendAdvertisingPush(record.value());
    }
}
