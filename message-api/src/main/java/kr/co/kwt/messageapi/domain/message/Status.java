package kr.co.kwt.messageapi.domain.message;

import kr.co.kwt.messageapi.domain.error.Assert;
import lombok.Value;

import java.time.LocalDateTime;

import static kr.co.kwt.messageapi.domain.error.DomainException.ErrorCode.STATUS_UNMODIFIABLE;

@Value
public class Status {

    public static Status PENDING = new Status(Stage.PENDING, LocalDateTime.now());

    Stage stage;
    LocalDateTime updatedAt;

    public Status next() {
        return new Status(stage.next(), LocalDateTime.now());
    }

    public boolean isTerminated() {
        return !(stage == Stage.PENDING || stage == Stage.SENDING);
    }

    public void validateTerminatedStatus() {
        Assert.isTrue(isTerminated(), STATUS_UNMODIFIABLE);
    }

    public enum Stage {
        PENDING,    // 발송 대기
        SENDING,    // 발송 중
        SENT,       // 발송 완료
        DELIVERED,  // 수신 완료
        FAILED,     // 발송 실패
        EXPIRED,    // 만료됨
        CANCELED,   // 취소됨
        ;

        Stage next() {
            return switch (this) {
                case PENDING -> SENDING;
                case SENDING, SENT -> SENT;
                default -> this;
            };
        }
    }
}
