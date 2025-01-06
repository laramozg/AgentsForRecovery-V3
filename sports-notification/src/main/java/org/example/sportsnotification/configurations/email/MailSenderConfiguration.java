package org.example.sportsnotification.configurations.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderConfiguration {
    private final MailSenderProperties mailSenderProperties;

    public MailSenderConfiguration(MailSenderProperties mailSenderProperties) {
        this.mailSenderProperties = mailSenderProperties;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailSenderProperties.getHost());
        mailSender.setPort(mailSenderProperties.getPort());
        mailSender.setUsername(mailSenderProperties.getUsername());
        mailSender.setPassword(mailSenderProperties.getPassword());

        mailSender.getJavaMailProperties().put("mail.transport.protocol", mailSenderProperties.getProtocol());
        mailSender.getJavaMailProperties().put("mail.debug", mailSenderProperties.getDebug());

        return mailSender;
    }

}
