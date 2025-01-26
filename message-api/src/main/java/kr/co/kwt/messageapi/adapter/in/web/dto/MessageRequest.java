package kr.co.kwt.messageapi.adapter.in.web.dto;

import kr.co.kwt.messageapi.application.port.in.SendMessageCommand;

import java.time.LocalDateTime;

public record MessageRequest(
        String type,
        String title,
        String content,
        String image,
        String to,
        String from,
        String priority,
        String channel,
        LocalDateTime reservationAt
) {
    public SendMessageCommand toCommand() {
        return new SendMessageCommand(title, content, image, channel, type, from, to, priority, reservationAt);
    }
}