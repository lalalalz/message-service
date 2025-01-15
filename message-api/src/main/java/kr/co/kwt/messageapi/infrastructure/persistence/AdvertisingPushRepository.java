package kr.co.kwt.messageapi.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisingPushRepository extends JpaRepository<AdvertisingPushEntity, Long> {
    List<AdvertisingPushEntity> findByToOrderByCreatedAtDesc(String recipient);
}