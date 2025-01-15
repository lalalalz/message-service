package kr.co.kwt.messageapi.domain.model;

public enum MessageType {
    EMAIL,
    PUSH;

    public static boolean isValid(String type) {
        try {
            MessageType.valueOf(type.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}