package kr.co.kwt.messagecore.domain;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static kr.co.kwt.messagecore.domain.Status.Retry;
import static kr.co.kwt.messagecore.domain.Status.Stage;

@NoArgsConstructor
public class MessageBuilder {

    private UUID id;
    private Type type;
    private Channel channel;
    private Header header;
    private Body body;
    private From from;
    private To to;
    private Status status;
    private Option option;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MessageBuilder(UUID id, Status status, From from) {
        this.id = id;
        this.status = status;
        this.from = from;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public MessageBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public MessageBuilder type(String type) {
        this.type = Type.valueOf(type);
        return this;
    }

    public MessageBuilder channel(String channel) {
        this.channel = Channel.valueOf(channel);
        return this;
    }

    public MessageBuilder header(String header) {
        this.header = new Header(header);
        return this;
    }

    public MessageBuilder body(String body, String image) {
        this.body = new Body(body, image);
        return this;
    }

    public MessageBuilder from(String from) {
        this.from = new From(from);
        return this;
    }

    public MessageBuilder to(String to) {
        this.to = new To(to);
        return this;
    }

    public MessageBuilder status(String stage, Integer retryCount, LocalDateTime updatedAt) {
        this.status = new Status(Stage.valueOf(stage), new Retry(retryCount), updatedAt);
        return this;
    }

    public MessageBuilder reservation(LocalDateTime reservedAt) {
        this.option = new Option(new Option.Reservation(reservedAt), option.getPriority());
        return this;
    }

    public MessageBuilder priority(String priority) {
        this.option = new Option(option.getReservation(), Option.Priority.valueOf(priority));
        return this;
    }

    public MessageBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public MessageBuilder updatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public Message build() {
        // TODO 유효성 체크....
        return new Message(id, type, channel, header, body, from, to, status, option, createdAt, updatedAt);
    }
}