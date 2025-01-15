package kr.co.kwt.messageapi.domain.model;

public enum MessagePurpose {
    INFORMATIONAL,
    ADVERTISING;

    public static boolean isValid(String purpose) {
        try {
            MessagePurpose.valueOf(purpose.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}