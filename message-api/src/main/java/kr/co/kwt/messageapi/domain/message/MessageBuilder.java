package kr.co.kwt.messageapi.domain.message;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static kr.co.kwt.messageapi.domain.message.Option.OptionType.valueOf;

@NoArgsConstructor
public class MessageBuilder {

    private Long id;
    private Type type;
    private Channel channel;
    private Header header;
    private Body body;
    private From from;
    private To to;
    private Status status;
    private List<Option> options = new ArrayList<>();

    public MessageBuilder(Status status, From from) {
        this.status = status;
        this.from = from;
    }

    public MessageBuilder id(Long id) {
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

    public MessageBuilder status(String stage, LocalDateTime updatedAt) {
        this.status = new Status(Status.Stage.valueOf(stage), updatedAt);
        return this;
    }

    public MessageBuilder options(Map<String, String> options) {
        this.options = options
                .entrySet()
                .stream()
                .map(entry -> new Option(valueOf(entry.getKey()), entry.getValue()))
                .toList();

        return this;
    }

    public Message build() {
        Message message = new Message(id, type, channel, header, body, from, to, status, options);
        message.validateMessage();
        return message;
    }
}