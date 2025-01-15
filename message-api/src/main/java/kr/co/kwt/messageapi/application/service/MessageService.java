package kr.co.kwt.messageapi.application.service;

import kr.co.kwt.messageapi.application.port.in.MessageUseCase;
import kr.co.kwt.messageapi.domain.model.Message;
import kr.co.kwt.messageapi.domain.model.MessagePurpose;
import kr.co.kwt.messageapi.domain.model.MessageType;
import kr.co.kwt.messageapi.domain.port.out.MessageProducer;
import kr.co.kwt.messageapi.infrastructure.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService implements MessageUseCase {
    private final MessageProducer messageProducer;
    private final InformationalEmailRepository informationalEmailRepository;
    private final InformationalPushRepository informationalPushRepository;
    private final AdvertisingEmailRepository advertisingEmailRepository;
    private final AdvertisingPushRepository advertisingPushRepository;

    @Override
    public void sendMessage(Message message) {
        // 1. Save to appropriate table
        saveMessageEntity(message);

        // 2. Send to Kafka
        messageProducer.send(message);
    }

    private void saveMessageEntity(Message message) {
        MessageType type = MessageType.valueOf(message.getType().toUpperCase());
        MessagePurpose purpose = MessagePurpose.valueOf(message.getPurpose().toUpperCase());

        switch (purpose) {
            case INFORMATIONAL -> {
                switch (type) {
                    case EMAIL -> informationalEmailRepository.save(InformationalEmailEntity.fromDomain(message));
                    case PUSH -> informationalPushRepository.save(InformationalPushEntity.fromDomain(message));
                }
            }
            case ADVERTISING -> {
                switch (type) {
                    case EMAIL -> advertisingEmailRepository.save(AdvertisingEmailEntity.fromDomain(message));
                    case PUSH -> advertisingPushRepository.save(AdvertisingPushEntity.fromDomain(message));
                }
            }
        }
    }
}