package kr.co.kwt.messagecore.domain;

import lombok.Value;

import java.util.regex.Pattern;

import static kr.co.kwt.messagecore.domain.DomainException.ErrorCode.*;


@Value
public class To {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PUSH_TOKEN_REGEX = "^[A-Za-z0-9\\-\\_]{10,}$";

    String identifier;

    public void validate(Channel channel) {
        if (Channel.EMAIL.equals(channel)) {
            validateEmailFormat();
        }
        else if (Channel.PUSH.equals(channel)) {
            validatePushTokenFormat();
        }
        else {
            throw new DomainException(CHANNEL_INVALID);
        }
    }

    private void validateEmailFormat() {
        if (!Pattern.matches(EMAIL_REGEX, identifier)) {
            throw new DomainException(TO_NOT_MATCHED_EMAIL_REGEX, identifier);
        }
    }

    private void validatePushTokenFormat() {
        if (!Pattern.matches(PUSH_TOKEN_REGEX, identifier)) {
            throw new DomainException(TO_NOT_MATCHED_PUSH_REGEX, identifier);
        }
    }
}
