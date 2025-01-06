package org.example.sportsnotification;

import org.example.sportsnotification.configurations.email.MailSenderProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MailSenderProperties.class)
public class SportsNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsNotificationApplication.class, args);
    }

}
