package kr.co.kwt.messagecore.application.port.out;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class SaveMessageEvent {

    UUID id;
    String type;
    String channel;
    LocalDateTime createdAt;
}
