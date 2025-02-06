package kr.co.kwt.messageapi.application.service;

import jakarta.validation.Valid;
import kr.co.kwt.messageapi.application.port.in.SendMessageCommand;
import kr.co.kwt.messageapi.application.port.in.SendMessageResult;
import kr.co.kwt.messageapi.application.port.in.SendMessageUseCase;
import kr.co.kwt.messageapi.application.port.out.MessageEventProducerPort;
import kr.co.kwt.messageapi.application.port.out.SaveMessageEvent;
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

    private final MessageEventProducerPort messageEventProducerPort;
    private final SaveMessagePort saveMessagePort;

    @Override
    public SendMessageResult sendMessage(@Valid SendMessageCommand sendMessageCommand) {
        log.info("sendMessageCommand : {}", sendMessageCommand);
        Message message = createMessage(sendMessageCommand);
        doSendMessage(message);

        return SendMessageResult
                .builder()
                .messageId(message.getId())
                .sentAt(message.getCreatedAt())
                .build();
    }

    private Message createMessage(SendMessageCommand sendMessageCommand) {
        return Message
                .defaultBuilder()
                .type(sendMessageCommand.getType())
                .channel(sendMessageCommand.getChannel())
                .header(sendMessageCommand.getTitle())
                .body(sendMessageCommand.getContent(), sendMessageCommand.getImage())
                .to(sendMessageCommand.getTo())
                .build();
    }

    private void doSendMessage(Message message) {
        saveMessagePort.save(message);
        messageEventProducerPort.send(SaveMessageEvent
                .builder()
                .id(message.getId())
                .type(message.getType().name())
                .channel(message.getChannel().name())
                .build());
    }
}