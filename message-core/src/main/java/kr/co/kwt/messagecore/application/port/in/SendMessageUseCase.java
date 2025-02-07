package kr.co.kwt.messagecore.application.port.in;

public interface SendMessageUseCase {
    SendMessageResult sendMessage(SendMessageCommand sendMessageCommand);
}
