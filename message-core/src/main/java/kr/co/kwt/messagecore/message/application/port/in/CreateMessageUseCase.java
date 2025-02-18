package kr.co.kwt.messagecore.message.application.port.in;

import kr.co.kwt.messagecore.message.application.port.in.command.CreateMessageCommand;

import static kr.co.kwt.messagecore.message.application.port.in.command.CreateMessageCommand.CreateMessageResult;

public interface CreateMessageUseCase {
    CreateMessageResult createMessage(CreateMessageCommand createMessageCommand);
}
