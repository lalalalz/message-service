package kr.co.kwt.messagecore.application.service;

import kr.co.kwt.messagecore.application.port.in.SearchMessageUseCase;
import kr.co.kwt.messagecore.application.port.in.query.SearchMessageResult;
import kr.co.kwt.messagecore.application.port.out.LoadMessagePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static kr.co.kwt.messagecore.application.service.BusinessException.ErrorCode;

@Service
@RequiredArgsConstructor
class SearchMessageService implements SearchMessageUseCase {

    private final LoadMessagePort loadMessagePort;

    @Override
    public SearchMessageResult searchMessage(UUID id) {
        return loadMessagePort
                .findById(id)
                .map(SearchMessageResult::of)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCH_INVALID_ID));
    }
}
