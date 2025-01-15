package kr.co.kwt.messageapi.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "recipient", nullable = false)
    private String to;

    private String imagePath;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    protected BaseMessageEntity(String title, String content, String to,
                                String imagePath, MessageStatus status) {
        this.title = title;
        this.content = content;
        this.to = to;
        this.imagePath = imagePath;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }
}