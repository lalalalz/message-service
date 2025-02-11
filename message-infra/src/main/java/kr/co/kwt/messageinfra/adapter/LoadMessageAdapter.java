package kr.co.kwt.messageinfra.adapter;

import kr.co.kwt.messagecore.application.port.out.LoadMessagePort;
import kr.co.kwt.messagecore.domain.Message;
import kr.co.kwt.messageinfra.adapter.persistence.mongo.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
class LoadMessageAdapter implements LoadMessagePort {

    private final MessageRepository messageRepository;

    @Override
    public Optional<Message> findById(UUID id) {
        return messageRepository.findById(id);
    }
}
