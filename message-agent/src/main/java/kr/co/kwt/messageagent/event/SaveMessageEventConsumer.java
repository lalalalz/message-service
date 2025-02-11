package kr.co.kwt.messageagent.event;

import kr.co.kwt.messageagent.service.EmailService;
import kr.co.kwt.messageagent.service.PushService;
import kr.co.kwt.messagecore.application.port.in.SearchMessageUseCase;
import kr.co.kwt.messagecore.application.port.in.SendMessageUseCase;
import kr.co.kwt.messagecore.application.port.out.event.MessageEvent;
import kr.co.kwt.messagecore.application.port.out.event.SaveMessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaveMessageEventConsumer {

    private final PushService pushService;
    private final EmailService emailService;
    private final SendMessageUseCase sendMessageUseCase;
    private final SearchMessageUseCase searchMessageUseCase;

    @KafkaListener(topics = "${kafka.topic.informational.email}")
    public void consumeInformationalEmail(ConsumerRecord<String, MessageEvent> record) {
        MessageEvent messageEvent = record.value();
        emailService.sendEmail(messageEvent);
    }

    @KafkaListener(topics = "${kafka.topic.advertising.email}")
    public void consumeAdvertisingEmail(ConsumerRecord<String, SaveMessageEvent> record) {
        MessageEvent messageEvent = record.value();
        emailService.sendEmail(messageEvent);
    }

    @KafkaListener(topics = "${kafka.topic.informational.email}")
    public void consumeInformationalPush(ConsumerRecord<String, SaveMessageEvent> record) {
//        pushService.sendPush(record.value());
    }

    @KafkaListener(topics = "${kafka.topic.advertising.push}")
    public void consumeAdvertisingPush(ConsumerRecord<String, SaveMessageEvent> record) {
//        pushService.sendPush(record.value());
    }
}
