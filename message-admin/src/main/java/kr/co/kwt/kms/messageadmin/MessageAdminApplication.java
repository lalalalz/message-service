package kr.co.kwt.kms.messageadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "kr.co.kwt.**")
public class MessageAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageAdminApplication.class, args);
    }

}
