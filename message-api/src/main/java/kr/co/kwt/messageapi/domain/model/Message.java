package kr.co.kwt.messageapi.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Message {
    private final String type;
    private final String purpose;
    private final String title;
    private final String content;
    private final String to;
    private final String imagePath;

    public static class MessageBuilder {
        public Message build() {
            if (!MessageType.isValid(type)) {
                throw new IllegalArgumentException("Invalid message type: " + type);
            }
            if (!MessagePurpose.isValid(purpose)) {
                throw new IllegalArgumentException("Invalid message purpose: " + purpose);
            }
            return new Message(type, purpose, title, content, to, imagePath);
        }
    }
}