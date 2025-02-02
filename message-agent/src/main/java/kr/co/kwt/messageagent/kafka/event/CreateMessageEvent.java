package kr.co.kwt.messageagent.kafka.event;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class CreateMessageEvent {

    UUID id;
    String type;
    String channel;
    String title;
    String content;
    String image;
    String to;
    String from;
    String priority;
    String reservation;
    String retry;
    LocalDateTime createdAt;
}
