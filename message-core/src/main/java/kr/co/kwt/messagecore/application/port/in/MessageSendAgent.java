package kr.co.kwt.messagecore.application.port.in;

import java.util.UUID;

@FunctionalInterface
public interface MessageSendAgent {
    void run(UUID id);
}
