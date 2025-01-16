package kr.co.kwt.messageapi.common.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionResponse(
        String status,
        String message
) {

    public static ExceptionResponse error(String message) {
        return new ExceptionResponse("ERROR", message);
    }
}