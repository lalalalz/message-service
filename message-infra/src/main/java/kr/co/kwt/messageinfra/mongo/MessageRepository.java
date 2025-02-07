package kr.co.kwt.messageinfra.mongo;

import kr.co.kwt.messagecore.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MessageRepository extends MongoRepository<Message, UUID> {

}
