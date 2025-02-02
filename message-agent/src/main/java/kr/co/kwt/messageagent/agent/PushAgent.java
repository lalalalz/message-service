package kr.co.kwt.messageagent.agent;

import kr.co.kwt.messageagent.kafka.event.CreateMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class PushAgent {
    
    public void sendInformationalPush(CreateMessageEvent value) {
    }

    public void sendAdvertisingPush(CreateMessageEvent value) {
    }
}
