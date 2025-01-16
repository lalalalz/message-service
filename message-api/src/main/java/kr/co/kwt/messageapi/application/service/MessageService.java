package kr.co.kwt.messageapi.application.service;

import kr.co.kwt.messageapi.application.port.in.MessageUseCase;
import kr.co.kwt.messageapi.application.port.out.SaveMessagePort;
import kr.co.kwt.messageapi.domain.model.Message;
import kr.co.kwt.messageapi.application.port.out.MessageProducerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageService implements MessageUseCase {
    private final MessageProducerPort messageProducerPort;
    private final SaveMessagePort saveMessagePort;

    @Override
    public void sendMessage(Message message) {
        // 1. 영속성 데이터 저장
        saveMessagePort.save(message);

        // 2. 메세지 발행
        messageProducerPort.send(message);
    }
}