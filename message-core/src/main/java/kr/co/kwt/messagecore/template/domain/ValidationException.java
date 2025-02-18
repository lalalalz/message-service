package kr.co.kwt.messagecore.template.domain;

import lombok.AllArgsConstructor;

public class ValidationException extends RuntimeException {

    private ErrorCode errorCode;

    public ValidationException() {
        super(ErrorCode.DEFAULT.message);
        this.errorCode = ErrorCode.DEFAULT;
    }

    public ValidationException(ErrorCode errorCode) {
        super(errorCode.message);
        this.errorCode = errorCode;
    }

    @AllArgsConstructor
    public enum ErrorCode {
        DEFAULT("템플릿 변수를 올바르게 설정해주세요."),
        LENGTH("템플릿 변수의 길이가 올바르지 않습니다."),
        ;

        private final String message;
    }
}
