package kr.co.kwt.messagecore.template.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DomainException extends RuntimeException {

    private ErrorCode errorCode;
    private Object[] args;

    public DomainException() {
        super(ErrorCode.DEFAULT.message);
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
        DEFAULT("템플릿 도메인 예외가 발생했습니다."),

        // ARGS
        RENDER_ARGS_COUNT("템플릿 변수의 개수가 올바르지 않습니다."),
        RENDER_ARGS_NO_MATCH("템플릿 변수가 매칭되지 않습니다."),
        ;

        private final String message;
    }
}
