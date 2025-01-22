package kr.co.kwt.messageapi.application.port.in;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class SendMessageCommand {

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

    @NotBlank(message = "Priority is required")
    @Pattern(regexp = "^(URGENT|HIGH|NORMAL|LOW)$", message = "Invalid message priority")
    private String priority;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Future(message = "예약 시간은 현재 시간 이후여야 합니다")
    @NotNull(message = "예약 시간은 필수입니다")
    private LocalDateTime reservationAt;
}
