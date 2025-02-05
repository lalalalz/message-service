package kr.co.kwt.messageapi.domain.message;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class History {

    Status status;
    Note note;
    LocalDateTime createdAt;

    @Value
    static class Note {
        public static final Note NONE = new Note(null);
        String description;
    }
}
