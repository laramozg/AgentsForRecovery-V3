package org.example.sportsnotification.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.sportsnotification.models.EmailNotification;
import org.example.sportsnotification.services.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaNotificationConsumer {
    private final EmailSender emailSender;
    private final Logger logger = LoggerFactory.getLogger(KafkaNotificationConsumer.class);

    public KafkaNotificationConsumer(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @KafkaListener(
            topics = "${spring.kafka.topics.notification-email-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(String emailNotification) {
        logger.info("Received email - " + emailNotification);
        try {
            emailSender.sendEmail(new ObjectMapper().registerModule(new JavaTimeModule()).readValue(emailNotification, EmailNotification.class));
        } catch (JsonProcessingException e) {
            logger.error("Error while sending email - " + emailNotification, e);
        }
    }
}
