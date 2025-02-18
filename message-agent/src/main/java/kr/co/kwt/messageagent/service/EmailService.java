package kr.co.kwt.messageagent.service;

import kr.co.kwt.messagecore.message.application.port.in.SearchMessageUseCase;
import kr.co.kwt.messagecore.message.application.port.in.SendMessageUseCase;
import kr.co.kwt.messagecore.message.application.port.in.command.SendMessageCommand;
import kr.co.kwt.messagecore.message.application.port.in.command.SendMessageCommand.AgentTask;
import kr.co.kwt.messagecore.message.application.port.in.query.SearchMessageQuery;
import kr.co.kwt.messagecore.message.application.port.out.event.MessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static kr.co.kwt.messagecore.message.application.port.in.query.SearchMessageQuery.SearchMessageResult;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SendMessageUseCase sendMessageUseCase;
    private final SearchMessageUseCase searchMessageUseCase;

    public void sendEmail(MessageEvent messageEvent) {
        sendMessageUseCase.sendMessage(new SendMessageCommand(messageEvent.getId(), doSendEmail()));
    }

    private AgentTask doSendEmail() {
        return id -> {
            SearchMessageQuery searchMessageQuery = new SearchMessageQuery(id);
            javaMailSender.send(getSimpleMailMessage(searchMessageUseCase.searchMessage(searchMessageQuery)));
        };
    }

    private SimpleMailMessage getSimpleMailMessage(SearchMessageResult searchMessageResult) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(searchMessageResult.getFrom());
        simpleMailMessage.setTo(searchMessageResult.getTo());
        simpleMailMessage.setSubject(searchMessageResult.getHeader());
        simpleMailMessage.setText(searchMessageResult.getBody());
        return simpleMailMessage;
    }
}
