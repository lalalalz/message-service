package kr.co.kwt.messageapi.adapter;

import kr.co.kwt.messageapi.domain.message.Channel;
import kr.co.kwt.messageapi.domain.message.Message;
import kr.co.kwt.messageapi.domain.message.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagePersistenceAdapter implements SaveMessagePort {
//    private final InformationalEmailRepository informationalEmailRepository;
//    private final InformationalPushRepository informationalPushRepository;
//    private final AdvertisingEmailRepository advertisingEmailRepository;
//    private final AdvertisingPushRepository advertisingPushRepository;

    @Override
    public void save(Message message) {
        Type type = Type.valueOf(message.getType().name());
        Channel channel = Channel.valueOf(message.getChannel().name());

        switch (type) {
            case INFORMATIONAL -> {
                switch (channel) {
                    /*case EMAIL -> informationalEmailRepository.save(InformationalEmailEntity.fromDomain(message));
                    case PUSH -> informationalPushRepository.save(InformationalPushEntity.fromDomain(message));*/
                }
            }
            case ADVERTISING -> {
                switch (channel) {
//                    case EMAIL -> advertisingEmailRepository.save(AdvertisingEmailEntity.fromDomain(message));
//                    case PUSH -> advertisingPushRepository.save(AdvertisingPushEntity.fromDomain(message));
                }
            }
        }
    }
}