package kr.co.kwt.messageapi.domain.message;

import lombok.Value;

import static kr.co.kwt.messageapi.domain.error.Assert.isTrue;
import static kr.co.kwt.messageapi.domain.error.DomainException.ErrorCode.HEADER_INVALID_LENGTH;

@Value
public class Header {

    String text;
//    String image;

    public void validate() {
        validateLength();
    }

    private void validateLength() {
        isTrue(text.isEmpty() || text.length() > 300, HEADER_INVALID_LENGTH);
    }
}
