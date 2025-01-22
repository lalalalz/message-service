package kr.co.kwt.messageapi.domain.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static kr.co.kwt.messageapi.domain.error.Assert.*;
import static kr.co.kwt.messageapi.domain.error.DomainException.ErrorCode.*;
import static lombok.AccessLevel.PACKAGE;

@Getter
@AllArgsConstructor(access = PACKAGE)
public class Message {

    private Long id;
    private Type type;
    private Channel channel;
    private Header header;
    private Body body;
    private From from;
    private To to;
    private Status status;
    private List<Option> options;

    public static MessageBuilder emptyBuilder() {
        return new MessageBuilder();
    }

    public static MessageBuilder defaultBuilder() {
        return new MessageBuilder(Status.PENDING, From.SYSTEM);
    }

    public void addOption(Option newOption) {
        isTerminatedStatus();
        hasExceededOptionLimit();
        options.add(newOption);
    }

    public void changeChannel(Channel newChannel) {
        isTerminatedStatus();
        channel = newChannel;
    }

    public void changeType(Type newType) {
        isTerminatedStatus();
        type = newType;
    }

    public void changeHeader(Header newHeader) {
        isTerminatedStatus();
        newHeader.validate();
        header = newHeader;
    }

    public void changeBody(Body newBody) {
        isTerminatedStatus();
        newBody.validate();
        body = newBody;
    }

    public void changeStatus(Status newStatus) {
        isTerminatedStatus();
        status = newStatus;
    }

    public void changeFrom(From newFrom) {
        isTerminatedStatus();
        from = newFrom;
    }

    public void changeTo(To newTo) {
        isTerminatedStatus();
        isChannelDefined();
        newTo.validate(channel);
    }

    public void validateMessage() {
        notNull(channel, CHANNEL_UNDEFINED);
        notNull(header, HEADER_UNDEFINED);
        notNull(body, BODY_UNDEFINED);
        notNull(from, FROM_UNDEFINED);
        notNull(to, TO_UNDEFINED);
        notNull(status, STATUS_UNDEFINED);
        notNull(options, OPTION_UNDEFINED);

        options.forEach(Option::validate);
        header.validate();
        body.validate();
        to.validate(channel);
    }

    private void isChannelDefined() {
        isNull(type, CHANNEL_INVALID);
    }

    private void hasExceededOptionLimit() {
        isTrue(options.size() >= 3, OPTION_MAX_LIMIT);
    }

    private void isTerminatedStatus() {
        isTrue(status.isTerminated(), STATUS_UNMODIFIABLE);
    }
}