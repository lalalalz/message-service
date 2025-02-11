package kr.co.kwt.messagecore.application.port.in.command;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class CreateMessageResult {
    UUID messageId;
    LocalDateTime createdAt;
}
