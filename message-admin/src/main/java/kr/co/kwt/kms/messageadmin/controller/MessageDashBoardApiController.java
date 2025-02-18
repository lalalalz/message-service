package kr.co.kwt.kms.messageadmin.controller;

import kr.co.kwt.kms.messageadmin.service.MessageDashBoardService;
import kr.co.kwt.messagecore.message.application.port.in.query.SearchMessageQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageDashBoardApiController {

    private final MessageDashBoardService messageDashBoardService;

    @GetMapping("/api/message/{id}")
    public SearchMessageQuery.SearchMessageResult searchMessage(@PathVariable String id) {
        return messageDashBoardService.getMessage(id);
    }

    @GetMapping("/api/messages")
    public Page<SearchMessageQuery.SearchMessageResult> searchMessages(Pageable pageable) {
        return messageDashBoardService.getMessagePages(pageable);
    }
}
