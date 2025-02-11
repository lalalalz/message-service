package kr.co.kwt.messagecore.application.port.in.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kr.co.kwt.messagecore.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CreateMessageCommand {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;
    private String image;

    @NotBlank(message = "Channel is required")
    @Pattern(regexp = "^(EMAIL|PUSH)$", message = "Invalid message channel")
    private String channel;

    @NotBlank(message = "Type is required")
    @Pattern(regexp = "^(INFORMATIONAL|ADVERTISING)$", message = "Invalid message type")
    private String type;
    private String from;

    @NotBlank(message = "To is required")
    private String to;

    public Message toDomain() {
        return Message
                .defaultBuilder()
                .type(type)
                .channel(channel)
                .header(title)
                .body(content, image)
                .to(to)
                .from(from)
                .build();
    }
}
