package kr.co.kwt.messageinfra.adapter.config.mongodb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "mongodb")
@ConditionalOnProperty(prefix = "mongodb", name = "enabled", havingValue = "true", matchIfMissing = true)
@Component
public class MongoDBProperties {

    private String host;
    private Integer port;
    private String authenticationDatabase;
    private String username;
    private String password;
    private String database;
}
