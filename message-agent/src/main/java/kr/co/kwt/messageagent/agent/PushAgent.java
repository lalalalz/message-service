package kr.co.kwt.messageagent.agent;

import kr.co.kwt.messageagent.kafka.event.SaveMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class PushAgent {

    public void sendInformationalPush(SaveMessageEvent value) {
    }

    public void sendAdvertisingPush(SaveMessageEvent value) {
    }
}
