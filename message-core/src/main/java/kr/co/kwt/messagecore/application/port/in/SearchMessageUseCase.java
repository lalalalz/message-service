package kr.co.kwt.messagecore.application.port.in;

import kr.co.kwt.messagecore.application.port.in.query.SearchMessageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SearchMessageUseCase {

    SearchMessageResult searchMessage(UUID id);

    Page<SearchMessageResult> searchMessages(Pageable pageable);

}
