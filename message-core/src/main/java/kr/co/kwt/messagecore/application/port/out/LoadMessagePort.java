package kr.co.kwt.messagecore.application.port.out;

import kr.co.kwt.messagecore.domain.Message;

import java.util.Optional;
import java.util.UUID;

public interface LoadMessagePort {

    Optional<Message> findById(UUID id);
}
