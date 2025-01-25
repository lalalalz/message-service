package kr.co.kwt.messageapi.domain.message;

import lombok.Value;

import static kr.co.kwt.messageapi.domain.error.Assert.isTrue;
import static kr.co.kwt.messageapi.domain.error.DomainException.ErrorCode.BODY_INVALID_LENGTH;

@Value
public class Body {

    String text;
    String image;

    public void validate() {
        validateLength();
    }

    private void validateLength() {
        isTrue(text.isEmpty() || text.length() > 1000, BODY_INVALID_LENGTH);
    }
}
