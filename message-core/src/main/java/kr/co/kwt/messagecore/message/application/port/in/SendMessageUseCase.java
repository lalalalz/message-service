package kr.co.kwt.messagecore.message.application.port.in;

import kr.co.kwt.messagecore.message.application.port.in.command.SendMessageCommand;

public interface SendMessageUseCase {

//    boolean sendMessage(Message message);
//
//    boolean sendMessage(UUID id);

    void sendMessage(SendMessageCommand sendMessageCommand);
}
