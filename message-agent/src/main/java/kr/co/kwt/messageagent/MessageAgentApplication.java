package kr.co.kwt.messageagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "kr.co.kwt.**")
@ConfigurationPropertiesScan("kr.co.kwt.**")
@SpringBootApplication(scanBasePackages = "kr.co.kwt.**")
public class MessageAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageAgentApplication.class, args);
    }
}
