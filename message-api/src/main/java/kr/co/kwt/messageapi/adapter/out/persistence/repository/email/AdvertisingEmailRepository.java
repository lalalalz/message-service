package kr.co.kwt.messageapi.adapter.out.persistence.repository.email;

import kr.co.kwt.messageapi.adapter.out.persistence.entity.email.AdvertisingEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisingEmailRepository extends JpaRepository<AdvertisingEmailEntity, Long> {
    List<AdvertisingEmailEntity> findByToOrderByCreatedAtDesc(String recipient);
}