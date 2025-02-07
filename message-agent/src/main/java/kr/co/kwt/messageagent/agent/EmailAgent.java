package kr.co.kwt.messageagent.agent;

import kr.co.kwt.messageagent.kafka.event.SaveMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class EmailAgent {

    public void sendAdvertisingEmail(SaveMessageEvent value) {
    }

    public void sendInformationalEmail(SaveMessageEvent value) {

    }
}
