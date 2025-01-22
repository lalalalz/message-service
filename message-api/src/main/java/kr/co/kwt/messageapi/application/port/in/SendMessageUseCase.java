package kr.co.kwt.messageapi.application.port.in;

public interface SendMessageUseCase {
    void sendMessage(SendMessageCommand sendMessageCommand);
}
