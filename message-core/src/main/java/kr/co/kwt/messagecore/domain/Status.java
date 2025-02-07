package kr.co.kwt.messagecore.domain;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Status {

    public static final Status PENDING = new Status(Stage.PENDING, Retry.DEFAULT, LocalDateTime.now());
    public static final Status CANCELED = new Status(Stage.CANCELED, Retry.DEFAULT, LocalDateTime.now());
    public static final Status FAILED = new Status(Stage.FAILED, Retry.DEFAULT, LocalDateTime.now());

    Stage stage;
    Retry retry;
    LocalDateTime updatedAt;

    enum Stage {
        PENDING,    // 발송 대기
        SENDING,    // 발송 중
        SENT,       // 발송 완료
        DELIVERED,  // 수신 완료
        FAILED,     // 발송 실패
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

    @Value
    static class Retry {
        static final Retry DEFAULT = new Retry(1);

        Integer count;

        Retry next() {
            return new Retry(count + 1);
        }
    }
}
