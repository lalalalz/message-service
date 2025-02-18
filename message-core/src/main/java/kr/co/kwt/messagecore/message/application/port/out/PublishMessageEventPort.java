package kr.co.kwt.messagecore.message.application.port.out;

import kr.co.kwt.messagecore.message.application.port.out.event.MessageEvent;

public interface PublishMessageEventPort {

    void publish(MessageEvent messageEvent);
}