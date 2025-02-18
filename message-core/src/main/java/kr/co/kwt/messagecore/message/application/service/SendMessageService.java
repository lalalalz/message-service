package kr.co.kwt.messagecore.message.application.service;

import kr.co.kwt.messagecore.message.application.port.in.SendMessageUseCase;
import kr.co.kwt.messagecore.message.application.port.in.command.SendMessageCommand;
import kr.co.kwt.messagecore.message.application.port.out.LoadMessagePort;
import kr.co.kwt.messagecore.message.application.port.out.SaveMessagePort;
import kr.co.kwt.messagecore.message.domain.DomainException;
import kr.co.kwt.messagecore.message.domain.Message;
import kr.co.kwt.messagecore.message.domain.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static kr.co.kwt.messagecore.message.application.port.in.command.SendMessageCommand.AgentTask;
import static kr.co.kwt.messagecore.message.application.service.BusinessException.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
class SendMessageService implements SendMessageUseCase {

    private final LoadMessagePort loadMessagePort;
    private final SaveMessagePort saveMessagePort;

    @Override
    public void sendMessage(SendMessageCommand sendMessageCommand) {
        Message message = getMessage(sendMessageCommand.getId());
        validateTerminatedStatus(message);
        doSend(message, sendMessageCommand.getAgentTask());
    }

    private void doSend(Message message, AgentTask agentTask) {
        try {
            if (isPendingStatus(message)) {
                message.advance();
            }

            agentTask.execute(message.getId());
            message.advance();
        }
        catch (Exception e) {
            message.suspend();
            throw new BusinessException(ErrorCode.SEND_AGENT_TASK_ERROR, e);
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
