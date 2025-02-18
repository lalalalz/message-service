package kr.co.kwt.messagecore.message.application.service;

import lombok.RequiredArgsConstructor;

public class BusinessException extends RuntimeException {

    private BusinessException.ErrorCode errorCode;
    private Object[] args;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.message);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, Exception e) {
        super(errorCode.message, e);
        this.errorCode = errorCode;
    }

    @RequiredArgsConstructor
    public enum ErrorCode {
        SCH_INVALID_ID("유효하지 않은 메시지 아이디입니다."),
        SEND_TERMINATED_STATUS("이미 처리가 완료된 메시지 입니다."),
        SEND_AGENT_TASK_ERROR("실제 발송 처리에 실패하였습니다.");

        private final String message;
    }
}
