package kr.co.kwt.messageapi.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.co.kwt.messageapi.domain.model.Message;

public record MessageRequest(
        @NotBlank(message = "Type is required")
        @Pattern(regexp = "^(EMAIL|PUSH)$", message = "Invalid message type")
        String type,

        @NotBlank(message = "Purpose is required")
        @Pattern(regexp = "^(INFORMATIONAL|ADVERTISING)$", message = "Invalid message purpose")
        String purpose,

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Content is required")
        String content,

        @NotBlank(message = "Recipient is required")
        String to,

        String imagePath,

        String grade
) {
    public Message toMessage() {
        return Message.builder()
                .type(type)
                .purpose(purpose)
                .title(title)
                .content(content)
                .to(to)
                .imagePath(imagePath)
                .build();
    }
}