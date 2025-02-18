package kr.co.kwt.messagecore.message.application.service;

import kr.co.kwt.messagecore.message.application.port.in.ChangeMessageStatusUseCase;
import kr.co.kwt.messagecore.message.application.port.out.LoadMessagePort;
import kr.co.kwt.messagecore.message.application.port.out.SaveMessagePort;
import kr.co.kwt.messagecore.message.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static kr.co.kwt.messagecore.message.application.service.BusinessException.ErrorCode;

@Service
@RequiredArgsConstructor
class ChangeMessageStatusService implements ChangeMessageStatusUseCase {

    private final LoadMessagePort loadMessagePort;
    private final SaveMessagePort saveMessagePort;

    @Override
    public void advance(UUID id) {
        Message message = loadMessagePort
                .findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCH_INVALID_ID));

        message.advance();
        saveMessagePort.save(message);
    }

    @Override
    public void suspend(UUID id) {
        Message message = loadMessagePort
                .findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCH_INVALID_ID));

        message.suspend();
        saveMessagePort.save(message);
    }

    @Override
    public void fail(UUID id) {
        Message message = loadMessagePort
                .findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCH_INVALID_ID));

        message.fail();
        saveMessagePort.save(message);
    }

    @Override
    public void cancel(UUID id) {
        Message message = loadMessagePort
                .findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCH_INVALID_ID));

        message.cancel();
        saveMessagePort.save(message);
    }
}
