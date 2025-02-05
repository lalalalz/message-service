package kr.co.kwt.messageapi.adapter.out.persistence.mongo;

import kr.co.kwt.messageapi.domain.message.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MessageRepository extends MongoRepository<Message, UUID> {
    
}
