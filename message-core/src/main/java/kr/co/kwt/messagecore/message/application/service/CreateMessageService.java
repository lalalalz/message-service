package kr.co.kwt.messagecore.message.application.service;

import jakarta.validation.Valid;
import kr.co.kwt.messagecore.message.application.port.in.CreateMessageUseCase;
import kr.co.kwt.messagecore.message.application.port.in.command.CreateMessageCommand;
import kr.co.kwt.messagecore.message.application.port.out.PublishMessageEventPort;
import kr.co.kwt.messagecore.message.application.port.out.SaveMessagePort;
import kr.co.kwt.messagecore.message.application.port.out.event.SaveMessageEvent;
import kr.co.kwt.messagecore.message.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static kr.co.kwt.messagecore.message.application.port.in.command.CreateMessageCommand.CreateMessageResult;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
class CreateMessageService implements CreateMessageUseCase {

    private final SaveMessagePort saveMessagePort;
    private final PublishMessageEventPort publishMessageEventPort;
//    private final TemplateEnginePort templateEnginePort;

    @Override
    public CreateMessageResult createMessage(@Valid CreateMessageCommand createMessageCommand) {
        log.info("CreateMessageCommand : {}", createMessageCommand);

        Message message = saveMessagePort.save(
                createMessageCommand.toDomain(null));

        publishMessageEventPort.publish(new SaveMessageEvent(
                message.getId(),
                message.getType().name(),
                message.getChannel().name(),
                LocalDateTime.now()));

        return CreateMessageResult
                .builder()
                .messageId(message.getId())
                .createdAt(message.getCreatedAt())
                .build();
    }
}