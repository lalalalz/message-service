package kr.co.kwt.messageapi.application.port.in;

import kr.co.kwt.messageapi.domain.model.Message;

public interface MessageUseCase {
    void sendMessage(Message message);
}
