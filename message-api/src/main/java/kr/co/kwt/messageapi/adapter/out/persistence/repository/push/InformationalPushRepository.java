package kr.co.kwt.messageapi.adapter.out.persistence.repository.push;

import kr.co.kwt.messageapi.adapter.out.persistence.entity.push.InformationalPushEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationalPushRepository extends JpaRepository<InformationalPushEntity, Long> {
    List<InformationalPushEntity> findByToOrderByCreatedAtDesc(String recipient);
}