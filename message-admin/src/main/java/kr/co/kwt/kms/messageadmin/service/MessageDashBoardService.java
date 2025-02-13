package kr.co.kwt.kms.messageadmin.service;

import kr.co.kwt.messagecore.application.port.in.SearchMessageUseCase;
import kr.co.kwt.messagecore.application.port.in.query.SearchMessageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageDashBoardService {

    private final SearchMessageUseCase searchMessageUseCase;

    public Page<SearchMessageResult> getMessagePages(Pageable pageable) {
        return searchMessageUseCase.searchMessages(pageable);
    }

    public SearchMessageResult getMessage(String id) {
        return searchMessageUseCase.searchMessage(UUID.fromString(id));
    }
}
