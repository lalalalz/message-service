package kr.co.kwt.messageinfra.mongo;

import kr.co.kwt.messagecore.application.port.out.SaveMessagePort;
import kr.co.kwt.messagecore.domain.Message;
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