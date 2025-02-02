package kr.co.kwt.messageagent.agent;

import kr.co.kwt.messageagent.kafka.event.CreateMessageEvent;
import org.springframework.stereotype.Component;

@Component
public class EmailAgent {

    public void sendEmail(CreateMessageEvent createMessageEvent) {

    }

    public void sendAdvertisingEmail(CreateMessageEvent value) {
    }

    public void sendInformationalEmail(CreateMessageEvent value) {

    }
}
