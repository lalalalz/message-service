package kr.co.kwt.messagecore.message.application.port.in;

import java.util.UUID;

public interface ChangeMessageStatusUseCase {

    void advance(UUID id);

    void suspend(UUID id);

    void fail(UUID id);

    void cancel(UUID id);
}
