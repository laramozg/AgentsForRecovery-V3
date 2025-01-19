package org.example.sportsfight.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.sportsfight.configurations.kafka.properties.KafkaTopicsProperties;
import org.example.sportsfight.kafka.messages.EmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
public class KafkaNotificationProducer {
    private final Logger logger = LoggerFactory.getLogger(KafkaNotificationProducer.class);
    private final KafkaTopicsProperties topicProps;
    private final KafkaTemplate<String, String> kafkaEmailTemplate;

    public KafkaNotificationProducer(KafkaTopicsProperties topicProps, KafkaTemplate<String, String> kafkaEmailTemplate) {
        this.topicProps = topicProps;
        this.kafkaEmailTemplate = kafkaEmailTemplate;
    }

    public void sendEmailNotification(EmailNotification email) {
        logger.info("Sending message - {} to - {}", email.getEmail() + " " + email.getText(), topicProps.getNotificationEmailTopic());
        try {
            kafkaEmailTemplate.send(topicProps.getNotificationEmailTopic(), email.getId().toString(), new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(email))
                    .whenComplete(this::addCallBack);
        } catch (JsonProcessingException e) {
            logger.error("Error sending message - {} to - {}", email, topicProps.getNotificationEmailTopic());
            logger.error(e.getMessage(), e);
        }
    }

    private <T extends SendResult<String, String>> void addCallBack(T result, Throwable ex) {
        if (ex == null) {
            logger.info("Message with id - {} sent successfully", result.getProducerRecord().key());
        } else {
            logger.warn("Message with id - {} sent failed, because - {}", result.getProducerRecord().key(), ex.getMessage());
        }
    }
}