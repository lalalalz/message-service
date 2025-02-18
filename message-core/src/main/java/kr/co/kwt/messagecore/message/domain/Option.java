package kr.co.kwt.messagecore.message.domain;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Option {

    Reservation reservation;
    Priority priority;

    @Value
    public static class Reservation {
        LocalDateTime reservedAt;
    }

    public enum Priority {
        NORMAL, HIGH
    }
}
