package kr.co.kwt.messagecore.application.port.out.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaveMessageEvent implements MessageEvent {

    private UUID id;
    private String type;
    private String channel;
    private LocalDateTime publishedAt;
}
