package kr.co.kwt.messageapi.adapter.out.persistence.repository.email;

import kr.co.kwt.messageapi.adapter.out.persistence.entity.email.InformationalEmailEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InformationalEmailRepository {
    List<InformationalEmailEntity> findByToOrderByCreatedAtDesc(String recipient);
}
//public interface InformationalEmailRepository extends JpaRepository<InformationalEmailEntity, Long> {
//    List<InformationalEmailEntity> findByToOrderByCreatedAtDesc(String recipient);
//}