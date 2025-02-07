package kr.co.kwt.messagecore.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

import static kr.co.kwt.messagecore.domain.DomainException.ErrorCode.STATUS_UNMODIFIABLE;
import static kr.co.kwt.messagecore.domain.Status.*;
import static lombok.AccessLevel.PACKAGE;

@Document(collation = "messages")
@Getter
@AllArgsConstructor(access = PACKAGE)
public class Message {

    private static final int MAX_RETRY_COUNT = 3;

    @Id
    private UUID id;
    private Type type;
    private Channel channel;
    private Header header;
    private Body body;
    private From from;
    private To to;
    private Status status;
    private Option option;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * <h2>builder 정적 팩토리 메소드</h2>
     */
    public static MessageBuilder emptyBuilder() {
        return new MessageBuilder();
    }

    public static MessageBuilder defaultBuilder() {
        return new MessageBuilder(UUID.randomUUID(), PENDING, From.SYSTEM);
    }

    /**
     * <h2>status 제어 메소드</h2>
     * <p>1. advanced : 현재 발송 상태를 진전시킨다.</p>
     * <p>2. suspend : 전진에 실패했을 때, 상태를 유보한다. (재시도 카운트 1증가)</p>
     * <p>3. fail : 현재 발송 상태를 실패로 처리한다. (재시도 모두 실패했을 경우, 자동으로 실패처리)</p>
     * <p>4. cancel : 현재 발송 상태를 취소로 처리한다. (단, PENDING 상태에서만 가능)</p>
     */
    public void advance() {
        validateTerminatedStatus();
        status = new Status(status.getStage().next(), Retry.DEFAULT, LocalDateTime.now());
        updatedAt = LocalDateTime.now();
    }

    public void suspend() {
        validateTerminatedStatus();
        validateMaxRetry();
        status = new Status(status.getStage(), status.getRetry().next(), LocalDateTime.now());
        updatedAt = LocalDateTime.now();
    }

    public void fail() {
        validateTerminatedStatus();
        status = new Status(Stage.FAILED, Retry.DEFAULT, LocalDateTime.now());
        updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        validateTerminatedStatus();
        status = new Status(Stage.CANCELED, Retry.DEFAULT, LocalDateTime.now());
        updatedAt = LocalDateTime.now();
    }

    private void validateTerminatedStatus() {
        if (status.getStage() == Stage.PENDING || status.getStage() == Stage.SENDING) {
            return;
        }

        throw new DomainException(STATUS_UNMODIFIABLE);
    }

    private void validateMaxRetry() {
        if (status.getRetry().getCount() >= MAX_RETRY_COUNT) {
            throw new DomainException();
        }
    }

    /**
     * <h2>Option 관련 메소드</h2>
     */
    public Option.Reservation getReservationOption() {
        return option.getReservation();
    }

    public Option.Priority getPriorityOption() {
        return option.getPriority();
    }

    public void changeReservationOption(Option.Reservation reservation) {
        validateTerminatedStatus();
        option = new Option(reservation, option.getPriority());
        updatedAt = LocalDateTime.now();
    }

    public void changePriorityOption(Option.Priority priority) {
        validateTerminatedStatus();
        option = new Option(option.getReservation(), priority);
        updatedAt = LocalDateTime.now();
    }
}