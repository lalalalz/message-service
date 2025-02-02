package kr.co.kwt.messageapi.application.service;

import jakarta.validation.Valid;
import kr.co.kwt.messageapi.application.port.in.SendMessageCommand;
import kr.co.kwt.messageapi.application.port.in.SendMessageUseCase;
import kr.co.kwt.messageapi.application.port.out.CreateMessageEvent;
import kr.co.kwt.messageapi.application.port.out.CreateMessageEventPort;
import kr.co.kwt.messageapi.domain.message.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendMessageService implements SendMessageUseCase {

    private final CreateMessageEventPort createMessageEventPort;

    @Override
    public void sendMessage(@Valid SendMessageCommand sendMessageCommand) {
        log.info("sendMessageCommand : {}", sendMessageCommand);

        createMessageEventPort.publish(CreateMessageEvent.of(Message
                .defaultBuilder()
                .id(UUID.randomUUID())
                .type(sendMessageCommand.getType())
                .channel(sendMessageCommand.getChannel())
                .header(sendMessageCommand.getTitle())
                .body(sendMessageCommand.getContent(), sendMessageCommand.getImage())
                .to(sendMessageCommand.getTo())
                .build()));
    }
}