package kr.co.kwt.messagecore.application.service;

import kr.co.kwt.messagecore.application.port.in.MessageSendAgent;
import kr.co.kwt.messagecore.application.port.in.SendMessageUseCase;
import kr.co.kwt.messagecore.application.port.out.LoadMessagePort;
import kr.co.kwt.messagecore.application.port.out.SaveMessagePort;
import kr.co.kwt.messagecore.domain.DomainException;
import kr.co.kwt.messagecore.domain.Message;
import kr.co.kwt.messagecore.domain.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static kr.co.kwt.messagecore.application.service.BusinessException.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
class SendMessageService implements SendMessageUseCase {

    private final LoadMessagePort loadMessagePort;
    private final SaveMessagePort saveMessagePort;

    @Override
    public void sendMessage(UUID id, MessageSendAgent agent) {
        Message message = getMessage(id);
        validateTerminatedStatus(message);
        doSend(message, agent);
    }

    private void doSend(Message message, MessageSendAgent agent) {
        try {
            if (isPendingStatus(message)) {
                message.advance();
            }
            agent.run(message.getId());
            message.advance();
        }
        catch (Exception e) {
            message.suspend();
            throw new BusinessException(ErrorCode.SEND_AGENT_ERROR, e);
        }
        finally {
            saveMessagePort.save(message);
        }
    }

    private Message getMessage(UUID id) {
        return loadMessagePort
                .findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.SCH_INVALID_ID));
    }

    private void validateTerminatedStatus(Message message) {
        try {
            message.validateTerminatedStatus();
        }
        catch (DomainException e) {
            throw new BusinessException(ErrorCode.SEND_TERMINATED_STATUS);
        }
    }

    private boolean isPendingStatus(Message message) {
        return message.getStatus().getStage().equals(Status.Stage.PENDING);
    }
}
