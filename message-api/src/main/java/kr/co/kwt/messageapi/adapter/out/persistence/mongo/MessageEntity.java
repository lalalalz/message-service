package kr.co.kwt.messageapi.adapter.out.persistence.mongo;

import kr.co.kwt.messageapi.domain.message.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document
public class MessageEntity {

    private UUID id;
    private String type;
    private String channel;
    private Header header;
    private Body body;
    private From from;
    private To to;
    private Status status;
    private List<Option> options;
}
