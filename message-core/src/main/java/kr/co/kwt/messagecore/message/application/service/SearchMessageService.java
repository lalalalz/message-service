package kr.co.kwt.messagecore.message.application.service;

import kr.co.kwt.messagecore.message.application.port.in.SearchMessageUseCase;
import kr.co.kwt.messagecore.message.application.port.in.query.SearchMessageQuery;
import kr.co.kwt.messagecore.message.application.port.out.LoadMessagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static kr.co.kwt.messagecore.message.application.port.in.query.SearchMessageQuery.SearchMessageResult;
import static kr.co.kwt.messagecore.message.application.service.BusinessException.ErrorCode;

@Service
@RequiredArgsConstructor
class SearchMessageService implements SearchMessageUseCase {

    private final LoadMessagePort loadMessagePort;

    @Override
    public SearchMessageResult searchMessage(SearchMessageQuery searchMessageQuery) {
        return loadMessagePort
                .findById(searchMessageQuery.getId())
                .map(SearchMessageResult::of)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCH_INVALID_ID));
    }

    @Override
    public Page<SearchMessageResult> searchMessages(Pageable pageable) {
        return loadMessagePort
                .findAll(pageable)
                .map(SearchMessageResult::of);
    }
}
