package kr.co.kwt.messageapi.adapter.in.web;

import kr.co.kwt.messageapi.adapter.in.web.dto.SendMessageRequest;
import kr.co.kwt.messagecore.message.application.port.in.CreateMessageUseCase;
import kr.co.kwt.messagecore.message.application.port.in.command.CreateMessageCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final CreateMessageUseCase createMessageUseCase;

    @PostMapping("/send")
    public ResponseEntity<CreateMessageCommand.CreateMessageResult> sendMessage(@RequestBody SendMessageRequest request) {
        CreateMessageCommand.CreateMessageResult body = createMessageUseCase.createMessage(request.toCommand());
        return ResponseEntity
                .accepted()
                .body(body);
    }
}