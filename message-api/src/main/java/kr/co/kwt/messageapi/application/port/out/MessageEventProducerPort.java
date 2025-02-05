package kr.co.kwt.messageapi.application.port.out;

public interface MessageEventProducerPort {
    void send(SaveMessageEvent saveMessageEvent);
}