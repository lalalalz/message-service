package kr.co.kwt.messagecore.application.port.in;

import java.util.UUID;

public interface SendMessageUseCase {

//    boolean sendMessage(Message message);
//
//    boolean sendMessage(UUID id);

    void sendMessage(UUID id, MessageSendAgent messageSendAgent);
}
