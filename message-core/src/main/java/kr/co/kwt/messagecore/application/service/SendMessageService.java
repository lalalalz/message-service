package kr.co.kwt.messagecore.application.service;

import kr.co.kwt.messagecore.application.port.in.SendMessageCommand;
import kr.co.kwt.messagecore.application.port.in.SendMessageResult;
import kr.co.kwt.messagecore.application.port.in.SendMessageUseCase;
import kr.co.kwt.messagecore.application.port.out.MessageEventProducerPort;
import kr.co.kwt.messagecore.application.port.out.SaveMessageEvent;
import kr.co.kwt.messagecore.application.port.out.SaveMessagePort;
import kr.co.kwt.messagecore.domain.Message;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SendMessageService implements SendMessageUseCase {

    private final SaveMessagePort saveMessagePort;
    private final MessageEventProducerPort messageEventProducerPort;

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