package kr.co.kwt.messageapi.adapter.out.persistence.mongo;

import kr.co.kwt.messageapi.application.port.out.SaveMessagePort;
import kr.co.kwt.messageapi.domain.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveMessageAdapter implements SaveMessagePort {

    private final MessageRepository messageRepository;

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }
}