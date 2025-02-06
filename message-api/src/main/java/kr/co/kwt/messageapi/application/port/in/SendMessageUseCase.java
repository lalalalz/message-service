package kr.co.kwt.messageapi.application.port.in;

public interface SendMessageUseCase {
    SendMessageResult sendMessage(SendMessageCommand sendMessageCommand);
}
