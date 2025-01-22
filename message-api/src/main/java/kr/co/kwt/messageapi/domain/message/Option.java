package kr.co.kwt.messageapi.domain.message;

import kr.co.kwt.messageapi.domain.error.DomainException;
import lombok.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static kr.co.kwt.messageapi.domain.error.Assert.isTrue;
import static kr.co.kwt.messageapi.domain.error.DomainException.ErrorCode.*;

@Value
public class Option {

    OptionType type;
    String value;

    public enum OptionType {
        PRIORITY,
        RESERVATION,
        RETRY,
    }

    void validate() {
        if (type.equals(OptionType.PRIORITY)) {
            validatePriority();
        }
        else if (type.equals(OptionType.RESERVATION)) {
            validateReservation();
        }
        else if (type.equals(OptionType.RETRY)) {
            validateRetry();
        }
        else {
            throw new DomainException(OPTION_INVALID, value);
        }
    }

    private void validatePriority() {
    }

    private void validateReservation() {
        try {
            LocalDateTime reservationTime = LocalDateTime.parse(value);
            isTrue(reservationTime.isBefore(LocalDateTime.now().plusMinutes(10)), OPTION_RESERVATION_BEFORE_TIME, value);
        }
        catch (DateTimeParseException e) {
            throw new DomainException(OPTION_RESERVATION_INVALID_VALUE, value);
        }
    }

    private void validateRetry() {
        try {
            int retryCount = Integer.parseInt(value);
            isTrue(retryCount < 0 || retryCount > 5, OPTION_RETRY_INVALID_COUNT);
        }
        catch (NumberFormatException e) {
            throw new DomainException(OPTION_RETRY_INVALID_COUNT, value);
        }
    }
}
