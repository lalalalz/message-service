package kr.co.kwt.messageinfra.adapter.persistence;

import kr.co.kwt.messagecore.message.application.port.out.LoadMessagePort;
import kr.co.kwt.messagecore.message.domain.Message;
import kr.co.kwt.messageinfra.adapter.persistence.mongo.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }
}
