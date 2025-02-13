package kr.co.kwt.messageagent.config.email;

import co.kr.kwt.starter.kms.modulekms.service.Secret;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class KmsSecret implements Secret {
    private String id;
    private String secretType;
    private String secretKey;
    private Object secretValue;
    private String description;
    private LocalDateTime lastModified;
}