package kr.co.kwt.messageapi.adapter.out.persistence.entity.email;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kr.co.kwt.messageapi.adapter.out.persistence.common.BaseMessageEntity;
import kr.co.kwt.messageapi.adapter.out.persistence.common.MessageStatus;
import kr.co.kwt.messageapi.domain.message.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "informational_emails")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InformationalEmailEntity extends BaseMessageEntity {
    protected InformationalEmailEntity(String title, String content, String to,
                                       String imagePath, MessageStatus status) {
        super(title, content, to, imagePath, status);
    }

    public static InformationalEmailEntity fromDomain(Message message) {
        return new InformationalEmailEntity(
                message.getTitle().getText(),
                message.getBody().getText(),
                message.getTo().getIdentity(),
                message.getBody().getImage(),
                MessageStatus.PENDING
        );
    }
}