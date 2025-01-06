package org.example.sportsnotification.services;

import org.example.sportsnotification.configurations.email.MailSenderProperties;
import org.example.sportsnotification.models.EmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {
    private final JavaMailSender mailSender;
    private final MailSenderProperties mailProps;
    private final Logger logger = LoggerFactory.getLogger(EmailSender.class);

    public EmailSender(JavaMailSender mailSender, MailSenderProperties mailProps) {
        this.mailSender = mailSender;
        this.mailProps = mailProps;
    }

    public void sendEmail(EmailNotification email) {
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setFrom(mailProps.getUsername());
        simpleMessage.setTo(email.email());
        simpleMessage.setSubject(email.title());
        simpleMessage.setText(email.text());

        logger.info("Sending message with id - " + email.id() + ", to " + email.email());
        mailSender.send(simpleMessage);
    }

}
