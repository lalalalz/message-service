package kr.co.kwt.messageagent.kafka.event;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class SaveMessageEvent {

    UUID id;
    String type;
    String channel;
    LocalDateTime createdAt;
}
