package kr.co.kwt.messageapi.infrastructure.web;

import jakarta.validation.Valid;
import kr.co.kwt.messageapi.application.port.in.MessageUseCase;
import kr.co.kwt.messageapi.infrastructure.web.dto.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageUseCase messageUseCase;

    @PostMapping
    public ResponseEntity<Void> sendMessage(@RequestBody @Valid MessageRequest request) {
        messageUseCase.sendMessage(request.toMessage());
        return ResponseEntity.accepted().build();
    }
}