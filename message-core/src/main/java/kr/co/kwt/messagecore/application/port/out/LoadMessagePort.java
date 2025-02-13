package kr.co.kwt.messagecore.application.port.out;

import kr.co.kwt.messagecore.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface LoadMessagePort {

    Optional<Message> findById(UUID id);

    Page<Message> findAll(Pageable pageable);
}
