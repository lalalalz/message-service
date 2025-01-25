package kr.co.kwt.messageapi.adapter.out.persistence.entity.push;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kr.co.kwt.messageapi.adapter.out.persistence.common.BaseMessageEntity;
import kr.co.kwt.messageapi.adapter.out.persistence.common.MessageStatus;
import kr.co.kwt.messageapi.domain.message.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "advertising_pushes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdvertisingPushEntity extends BaseMessageEntity {
    protected AdvertisingPushEntity(String title, String content, String to,
                                    String imagePath, MessageStatus status) {
        super(title, content, to, imagePath, status);
    }

    public static AdvertisingPushEntity fromDomain(Message message) {
        return new AdvertisingPushEntity(
                message.getTitle().getText(),
                message.getBody().getText(),
                message.getTo().getIdentity(),
                message.getBody().getImage(),
                MessageStatus.PENDING
        );
    }
}