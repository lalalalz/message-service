package kr.co.kwt.messageagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "kr.co.kwt.**")
public class MessageAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageAgentApplication.class, args);
    }
}