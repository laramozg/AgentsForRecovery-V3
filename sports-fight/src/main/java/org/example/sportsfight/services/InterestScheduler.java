package org.example.sportsfight.services;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.sportsfight.kafka.KafkaNotificationProducer;
import org.example.sportsfight.kafka.messages.EmailNotification;
import org.example.sportsfight.models.Performer;
import org.example.sportsfight.services.clients.dto.OrderDeadlineDto;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class InterestScheduler {
    private final FightService fightService;
    private final TemplateService templateService;
    private final KafkaNotificationProducer kafkaNotificationProducer;

    @Scheduled(cron = "${api.job.scheduler.process-debt}")
    public void fightDeadlineAndNotify() {
        log.info("Start check of deadline");
        List<Pair<OrderDeadlineDto, Performer>> pairs = fightService.calculateDeadline();
        pairs.forEach(pair -> {
            EmailNotification emailNotification = templateService.convertToEmailMessage(pair);
            kafkaNotificationProducer.sendEmailNotification(emailNotification);
        });
    }
}
