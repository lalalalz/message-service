package kr.co.kwt.messagecore.message.application.port.in;

import kr.co.kwt.messagecore.message.application.port.in.query.SearchMessageQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static kr.co.kwt.messagecore.message.application.port.in.query.SearchMessageQuery.SearchMessageResult;

public interface SearchMessageUseCase {

    SearchMessageResult searchMessage(SearchMessageQuery searchMessageQuery);

    Page<SearchMessageResult> searchMessages(Pageable pageable);

}
