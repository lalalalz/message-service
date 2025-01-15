package kr.co.kwt.messageapi.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationalPushRepository extends JpaRepository<InformationalPushEntity, Long> {
    List<InformationalPushEntity> findByToOrderByCreatedAtDesc(String recipient);
}