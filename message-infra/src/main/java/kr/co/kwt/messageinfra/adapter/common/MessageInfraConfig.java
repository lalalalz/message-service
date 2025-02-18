package kr.co.kwt.messageinfra.adapter.common;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "kr.co.kwt.**")  // 패키지 경로 수정s
@ConfigurationPropertiesScan(basePackages = "kr.co.kwt.**")
public class MessageInfraConfig {
}
