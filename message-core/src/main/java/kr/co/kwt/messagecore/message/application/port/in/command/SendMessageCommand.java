package kr.co.kwt.messagecore.message.application.port.in.command;

import lombok.Value;

import java.util.UUID;

@Value
public class SendMessageCommand {

    UUID id;
    AgentTask agentTask;

    @FunctionalInterface
    public interface AgentTask {
        void execute(UUID id);
    }
}
