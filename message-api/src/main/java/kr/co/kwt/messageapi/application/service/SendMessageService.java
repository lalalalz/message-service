package kr.co.kwt.messageapi.application.service;

import jakarta.validation.Valid;
import kr.co.kwt.messageapi.application.port.in.SendMessageCommand;
import kr.co.kwt.messageapi.application.port.in.SendMessageUseCase;
import kr.co.kwt.messageapi.application.port.out.MessageProducerPort;
import kr.co.kwt.messageapi.application.port.out.SaveMessagePort;
import kr.co.kwt.messageapi.domain.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SendMessageService implements SendMessageUseCase {

    private final MessageProducerPort messageProducerPort;
    private final SaveMessagePort saveMessagePort;

    @Override
    public void sendMessage(@Valid SendMessageCommand sendMessageCommand) {
        log.info("sendMessageCommand : {}", sendMessageCommand);
        messageProducerPort.send(Message
                .defaultBuilder()
                .type(sendMessageCommand.getType())
                .channel(sendMessageCommand.getChannel())
                .header(sendMessageCommand.getTitle())
                .body(sendMessageCommand.getContent(), sendMessageCommand.getImage())
                .to(sendMessageCommand.getTo())
                .build());
    }
}