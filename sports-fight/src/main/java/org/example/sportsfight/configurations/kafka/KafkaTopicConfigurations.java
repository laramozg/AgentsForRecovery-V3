package org.example.sportsfight.configurations.kafka;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.example.sportsfight.configurations.kafka.properties.KafkaTopicsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfigurations {

    private final KafkaTopicsProperties kafkaTopicsProperties;

    public KafkaTopicConfigurations(KafkaTopicsProperties kafkaTopicsProperties) {
        this.kafkaTopicsProperties = kafkaTopicsProperties;
    }

    @Bean
    public NewTopic notificationEmailTopic() {
        return TopicBuilder.name(kafkaTopicsProperties.getNotificationEmailTopic())
                .partitions(PARTITIONS)
                .replicas(REPLICAS)
                .configs(Map.of(
                        "min.insync.replicas", SYNC_REPLICAS
                ))
                .build();
    }

    private static final int PARTITIONS = 1;
    private static final int REPLICAS = 3;
    private static final String SYNC_REPLICAS = "2";
}