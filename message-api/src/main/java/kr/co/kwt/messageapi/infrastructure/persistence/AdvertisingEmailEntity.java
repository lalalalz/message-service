package kr.co.kwt.messageapi.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kr.co.kwt.messageapi.domain.model.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "advertising_emails")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdvertisingEmailEntity extends BaseMessageEntity {
    protected AdvertisingEmailEntity(String title, String content, String to,
                                    String imagePath, MessageStatus status) {
        super(title, content, to, imagePath, status);
    }

    public static AdvertisingEmailEntity fromDomain(Message message) {
        return new AdvertisingEmailEntity(
                message.getTitle(),
                message.getContent(),
                message.getTo(),
                message.getImagePath(),
                MessageStatus.PENDING
        );
    }
}