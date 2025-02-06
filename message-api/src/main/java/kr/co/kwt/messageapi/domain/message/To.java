package kr.co.kwt.messageapi.domain.message;

import kr.co.kwt.messageapi.common.assertion.Assert;
import lombok.Value;

import static kr.co.kwt.messageapi.domain.message.DomainException.ErrorCode.*;

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
        Assert.matchesPattern(identifier, EMAIL_REGEX, TO_NOT_MATCHED_EMAIL_REGEX, identifier);
    }

    private void validatePushTokenFormat() {
        Assert.matchesPattern(identifier, PUSH_TOKEN_REGEX, TO_NOT_MATCHED_PUSH_REGEX, identifier);
    }
}
