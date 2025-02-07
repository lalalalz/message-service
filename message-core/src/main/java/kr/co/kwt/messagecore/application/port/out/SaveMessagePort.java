package kr.co.kwt.messagecore.application.port.out;

import kr.co.kwt.messagecore.domain.Message;

public interface SaveMessagePort {
    Message save(Message message);
}