package kr.co.kwt.messageinfra.adapter.config.mongodb;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.UuidRepresentation;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.Assert;

@Slf4j
@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnClass(MongoClient.class)
@ConditionalOnProperty(prefix = "mongodb", name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(MongoDBProperties.class)
public class MongoAutoConfiguration {

    private final MongoDBProperties mongoDBProperties;

    @PostConstruct
    public void initialize() {
        Assert.notNull(mongoDBProperties.getHost(), "MongoDB host must not be null");
        Assert.notNull(mongoDBProperties.getDatabase(), "MongoDB database must not be null");
        Assert.notNull(mongoDBProperties.getUsername(), "MongoDB username must not be null");
        Assert.notNull(mongoDBProperties.getPassword(), "MongoDB password must not be null");

        log.info("MongoDB configuration initialized with host: {}", mongoDBProperties.getHost());
        log.info("MongoDB database: {}", mongoDBProperties.getDatabase());
    }

    @Bean
    @ConditionalOnMissingBean
    public MongoClient mongoClient() {
        String connectionString = String.format("mongodb://%s:%s@%s:%d/%s?authSource=%s",
                mongoDBProperties.getUsername(),
                mongoDBProperties.getPassword(),
                mongoDBProperties.getHost(),
                mongoDBProperties.getPort(),
                mongoDBProperties.getDatabase(),
                mongoDBProperties.getAuthenticationDatabase()
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        return MongoClients.create(settings);
    }

    @Bean
    @ConditionalOnMissingBean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, mongoDBProperties.getDatabase());
    }
}
