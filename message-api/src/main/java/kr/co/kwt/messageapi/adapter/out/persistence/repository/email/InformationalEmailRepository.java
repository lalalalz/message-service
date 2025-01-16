package kr.co.kwt.messageapi.adapter.out.persistence.repository.email;

import kr.co.kwt.messageapi.adapter.out.persistence.entity.email.InformationalEmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationalEmailRepository extends JpaRepository<InformationalEmailEntity, Long> {
    List<InformationalEmailEntity> findByToOrderByCreatedAtDesc(String recipient);
}