package kr.co.kwt.messageapi.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kr.co.kwt.messageapi.domain.model.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "informational_pushes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InformationalPushEntity extends BaseMessageEntity {
    protected InformationalPushEntity(String title, String content, String to,
                                       String imagePath, MessageStatus status) {
        super(title, content, to, imagePath, status);
    }

    public static InformationalPushEntity fromDomain(Message message) {
        return new InformationalPushEntity(
                message.getTitle(),
                message.getContent(),
                message.getTo(),
                message.getImagePath(),
                MessageStatus.PENDING
        );
    }
}