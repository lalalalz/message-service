package kr.co.kwt.messageinfra.adapter.persistence;

import kr.co.kwt.messagecore.message.application.port.out.SaveMessagePort;
import kr.co.kwt.messagecore.message.domain.Message;
import kr.co.kwt.messageinfra.adapter.persistence.mongo.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SaveMessageAdapter implements SaveMessagePort {

    private final MessageRepository messageRepository;

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }
}