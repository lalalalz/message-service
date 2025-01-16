package kr.co.kwt.messageapi.adapter.out.persistence.adapter;

import kr.co.kwt.messageapi.adapter.out.persistence.entity.email.AdvertisingEmailEntity;
import kr.co.kwt.messageapi.adapter.out.persistence.repository.email.AdvertisingEmailRepository;
import kr.co.kwt.messageapi.adapter.out.persistence.entity.push.AdvertisingPushEntity;
import kr.co.kwt.messageapi.adapter.out.persistence.repository.push.AdvertisingPushRepository;
import kr.co.kwt.messageapi.adapter.out.persistence.entity.email.InformationalEmailEntity;
import kr.co.kwt.messageapi.adapter.out.persistence.repository.email.InformationalEmailRepository;
import kr.co.kwt.messageapi.adapter.out.persistence.entity.push.InformationalPushEntity;
import kr.co.kwt.messageapi.adapter.out.persistence.repository.push.InformationalPushRepository;
import kr.co.kwt.messageapi.application.port.out.SaveMessagePort;
import kr.co.kwt.messageapi.domain.model.Message;
import kr.co.kwt.messageapi.domain.model.MessagePurpose;
import kr.co.kwt.messageapi.domain.model.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagePersistenceAdapter implements SaveMessagePort {
    private final InformationalEmailRepository informationalEmailRepository;
    private final InformationalPushRepository informationalPushRepository;
    private final AdvertisingEmailRepository advertisingEmailRepository;
    private final AdvertisingPushRepository advertisingPushRepository;

    @Override
    public void save(Message message) {
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