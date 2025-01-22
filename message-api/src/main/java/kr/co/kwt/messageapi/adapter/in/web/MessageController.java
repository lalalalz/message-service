package kr.co.kwt.messageapi.adapter.in.web;

import kr.co.kwt.messageapi.adapter.in.web.dto.MessageRequest;
import kr.co.kwt.messageapi.application.port.in.SendMessageUseCase;
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
    private final SendMessageUseCase sendMessageUseCase;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@RequestBody MessageRequest request) {
        sendMessageUseCase.sendMessage(request.toCommand());
        return ResponseEntity.accepted().build();
    }
}