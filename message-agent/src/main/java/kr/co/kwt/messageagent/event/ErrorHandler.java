package kr.co.kwt.messageagent.event;

import kr.co.kwt.messagecore.message.application.port.out.event.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;

@Slf4j
@Component
public class ErrorHandler extends DefaultErrorHandler {

    public ErrorHandler() {
        super(new FixedBackOff(1000L, 3));
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public void handleRemaining(
            Exception thrownException,
            List<ConsumerRecord<?, ?>> records,
            Consumer<?, ?> consumer,
            MessageListenerContainer container
    ) {
        ConsumerRecord<?, ?> record = records
                .stream()
                .findAny()
                .orElseThrow(() -> new RuntimeException(thrownException.getMessage()));

        log.error("메시지 발송 최종 실패, topic : {}, messageId : {}",
                record.topic(),
                ((MessageEvent) record.value()).getId(),
                thrownException);

        super.handleRemaining(thrownException, records, consumer, container);
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public boolean handleOne(
            Exception thrownException,
            ConsumerRecord<?, ?> record,
            Consumer<?, ?> consumer,
            MessageListenerContainer container
    ) {
        MessageEvent messageEvent = (MessageEvent) record.value();
        log.error("메시지 발송 실패, topic : {}, messageId : {}", record.topic(), messageEvent.getId(), thrownException);
        return super.handleOne(thrownException, record, consumer, container);
    }
}
