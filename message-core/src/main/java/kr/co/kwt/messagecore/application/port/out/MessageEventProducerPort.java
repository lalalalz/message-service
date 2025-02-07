package kr.co.kwt.messagecore.application.port.out;

public interface MessageEventProducerPort {
    void send(SaveMessageEvent saveMessageEvent);
}