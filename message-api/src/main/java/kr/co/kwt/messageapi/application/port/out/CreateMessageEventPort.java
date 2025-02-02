package kr.co.kwt.messageapi.application.port.out;

public interface CreateMessageEventPort {
    void publish(CreateMessageEvent createMessageEvent);
}