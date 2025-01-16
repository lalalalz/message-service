package kr.co.kwt.messageapi.adapter.out.persistence.repository.push;

import kr.co.kwt.messageapi.adapter.out.persistence.entity.push.AdvertisingPushEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisingPushRepository extends JpaRepository<AdvertisingPushEntity, Long> {
    List<AdvertisingPushEntity> findByToOrderByCreatedAtDesc(String recipient);
}