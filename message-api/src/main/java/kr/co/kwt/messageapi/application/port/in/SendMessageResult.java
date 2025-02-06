package kr.co.kwt.messageapi.application.port.in;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class SendMessageResult {
    UUID messageId;
    LocalDateTime sentAt;
}
