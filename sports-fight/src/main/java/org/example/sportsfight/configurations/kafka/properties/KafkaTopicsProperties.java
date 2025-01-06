package org.example.sportsfight.configurations.kafka.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.topics")
public class KafkaTopicsProperties {

    private final String notificationEmailTopic;

    public KafkaTopicsProperties(String notificationEmailTopic) {
        this.notificationEmailTopic = notificationEmailTopic;
    }

    public String getNotificationEmailTopic() {
        return notificationEmailTopic;
    }
}
