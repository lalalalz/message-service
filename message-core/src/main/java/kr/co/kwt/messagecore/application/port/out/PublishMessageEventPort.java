package kr.co.kwt.messagecore.application.port.out;

import kr.co.kwt.messagecore.application.port.out.event.MessageEvent;

public interface PublishMessageEventPort {
    
    void publish(MessageEvent messageEvent);
}