package kr.co.kwt.messageapi.adapter.in.web.dto;

import kr.co.kwt.messagecore.message.application.port.in.command.CreateMessageCommand;
import lombok.Value;

@Value
public class SendMessageRequest {
    String type;
    String titleText;
    String bodyText;
    String image;
    String to;
    String from;
    String channel;

    public CreateMessageCommand toCommand() {
        return new CreateMessageCommand(titleText, bodyText, image, channel, type, from, to);
    }
}