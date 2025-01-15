package kr.co.kwt.messageapi.domain.port.out;

import kr.co.kwt.messageapi.domain.model.Message;

public interface MessageProducer {
    void send(Message message);
}