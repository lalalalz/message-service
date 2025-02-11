package kr.co.kwt.messagecore.application.port.in;

import kr.co.kwt.messagecore.application.port.in.command.CreateMessageCommand;
import kr.co.kwt.messagecore.application.port.in.command.CreateMessageResult;

public interface CreateMessageUseCase {
    CreateMessageResult createMessage(CreateMessageCommand createMessageCommand);
}
