package kr.co.kwt.messageapi.application.port.out;

import kr.co.kwt.messageapi.domain.message.Message;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

import static kr.co.kwt.messageapi.domain.message.Option.OptionType.*;

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

    public static CreateMessageEvent of(Message message) {
        return new CreateMessageEvent(
                message.getId(),
                message.getType().name(),
                message.getChannel().name(),
                message.getHeader().getText(),
                message.getBody().getText(),
                message.getBody().getImage(),
                message.getTo().getIdentifier(),
                message.getFrom().getIdentifier(),
                message.getOption(PRIORITY).orElseGet(null).getValue(),
                message.getOption(RESERVATION).orElseGet(null).getValue(),
                message.getOption(RETRY).orElseGet(null).getValue(),
                LocalDateTime.now()
        );
    }
}
