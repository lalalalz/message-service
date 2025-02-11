package kr.co.kwt.messagecore.application.port.in;

import kr.co.kwt.messagecore.application.port.in.query.SearchMessageResult;

import java.util.UUID;

public interface SearchMessageUseCase {

    SearchMessageResult searchMessage(UUID id);
}
