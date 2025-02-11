package kr.co.kwt.messagecore.application.port.in.query;

import kr.co.kwt.messagecore.domain.Message;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class SearchMessageResult {

    UUID id;
    String type;
    String channel;
    String header;
    String body;
    String image;
    String to;
    String from;
    String stage;
    Integer retryCount;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    public static SearchMessageResult of(Message message) {
        return new SearchMessageResult(
                message.getId(),
                message.getType().name(),
                message.getChannel().name(),
                message.getHeader().getText(),
                message.getBody().getText(),
                message.getBody().getImage(),
                message.getTo().getIdentifier(),
                message.getFrom().getIdentifier(),
                message.getStatus().getStage().name(),
                message.getStatus().getRetry().getCount(),
                message.getCreatedAt(),
                message.getUpdatedAt()
        );
    }
}
