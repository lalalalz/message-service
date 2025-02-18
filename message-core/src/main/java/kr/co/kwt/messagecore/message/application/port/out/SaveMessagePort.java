package kr.co.kwt.messagecore.message.application.port.out;

import kr.co.kwt.messagecore.message.domain.Message;

public interface SaveMessagePort {
    Message save(Message message);
}