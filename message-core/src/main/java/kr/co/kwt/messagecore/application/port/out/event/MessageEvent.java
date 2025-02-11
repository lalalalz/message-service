package kr.co.kwt.messagecore.application.port.out.event;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface MessageEvent {

    UUID getId();

    String getType();

    String getChannel();

    LocalDateTime getPublishedAt();
}
