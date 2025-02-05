package kr.co.kwt.messageapi.domain.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

import static kr.co.kwt.messageapi.domain.message.DomainException.ErrorCode.DEFAULT;

public class DomainException extends RuntimeException {

    private ErrorCode errorCode;
    private Object[] args;

    public DomainException() {
        super(DEFAULT.message);
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(ErrorCode errorCode, Object... args) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.args = args;
    }

    public String getFormattedMessage() {
        if (args == null || args.length == 0) {
            return errorCode.getMessage();
        }
        return errorCode.getMessage() + ": " + Arrays.stream(args)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    @Getter
    @AllArgsConstructor
    public enum ErrorCode {
        DEFAULT("메시지 도메인 예외가 발생했습니다."),

        // ID
        ID_UNDEFINED("아이디가 지정되지 않았습니다."),

        // Options
        OPTION_DUPLICATED("옵션이 중복 되었습니다!"),
        OPTION_INVALID("유효하지 않은 옵션입니다"),
        OPTION_PRIORITY_INVALID_VALUE("긴급도 옵션의 값이 유효하지 않습니다."),
        OPTION_RESERVATION_INVALID_VALUE("예약 옵션의 시간값이 유효하지 않습니다."),
        OPTION_RESERVATION_BEFORE_TIME("예약 옵션의 시간은 현재 이후의 시간이어야 합니다."),
        OPTION_RETRY_INVALID_COUNT("유효하지 않은 재시도 요청수 입니다."),
        OPTION_COUNT_RANGE("옵션은 0~3개만 지정이 가능합니다."),
        OPTION_UNDEFINED("옵션이 지정되지 않았습니다."),

        // Status
        STATUS_UNMODIFIABLE("이미 처리된 메시지는 수정할 수 없습니다."),
        STATUS_UNDEFINED("상태값이 지정되지 않았습니다."),
        STATUS_ATTEMPT_MAX("더 이상 재시도할 수 없습니다."),

        // Header
        HEADER_INVALID_LENGTH("메시지의 제목은 1자 이상 300자 이하이어야 합니다."),
        HEADER_UNDEFINED("제목이 지정되지 않았습니다."),

        // Body
        BODY_INVALID_LENGTH("메시지의 본문은 1자 이상 1000자 이하이어야 합니다."),
        BODY_UNDEFINED("본문이 지정되지 않았습니다."),

        // Channel
        CHANNEL_UNDEFINED("메시지의 채널이 지정되지 않았습니다."),
        CHANNEL_INVALID("유효하지 않은 채널값 입니다."),

        // Type
        TYPE_UNDEFINED("타입이 지정되지 않았습니다."),
        TYPE_INVALID("유효하지 않은 타입값 입니다."),

        // From/To
        FROM_UNDEFINED("발신자가 지정되지 않았습니다."),
        TO_UNDEFINED("수신자가 지정되지 않았습니다."),
        TO_NOT_MATCHED_EMAIL_REGEX("이메일 형식의 값이 아닙니다."),
        TO_NOT_MATCHED_PUSH_REGEX("토큰 형식의 값이 아닙니다."),
        ;

        private final String message;
    }
}
