package kr.co.kwt.messageagent.service;

import kr.co.kwt.messagecore.application.port.in.MessageSendAgent;
import kr.co.kwt.messagecore.application.port.in.SearchMessageUseCase;
import kr.co.kwt.messagecore.application.port.in.SendMessageUseCase;
import kr.co.kwt.messagecore.application.port.in.query.SearchMessageResult;
import kr.co.kwt.messagecore.application.port.out.event.MessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SendMessageUseCase sendMessageUseCase;
    private final SearchMessageUseCase searchMessageUseCase;

    public void sendEmail(MessageEvent messageEvent) {
        sendMessageUseCase.sendMessage(messageEvent.getId(), doSendEmail());
    }

    private MessageSendAgent doSendEmail() {
        return id -> {
            SearchMessageResult searchMessageResult = searchMessageUseCase.searchMessage(id);
            javaMailSender.send(getSimpleMailMessage(searchMessageResult));
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
