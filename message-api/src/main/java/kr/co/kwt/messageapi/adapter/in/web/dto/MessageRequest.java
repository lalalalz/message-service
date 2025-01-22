package kr.co.kwt.messageapi.adapter.in.web.dto;

import kr.co.kwt.messageapi.application.port.in.SendMessageCommand;

public record MessageRequest(
        String type,
        String titleText,
        String bodyText,
        String image,
        String to,
        String from,
        String priority,
        String channel
) {
    public SendMessageCommand toCommand() {
        return new SendMessageCommand(titleText, bodyText, image, channel, type, from, to, priority);
    }
}