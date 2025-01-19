package org.example.sportsfight.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import lombok.extern.slf4j.Slf4j;
import org.example.sportsfight.kafka.messages.EmailNotification;
import org.example.sportsfight.models.Performer;
import org.example.sportsfight.services.clients.dto.OrderDeadlineDto;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {
    public EmailNotification convertToEmailMessage(Pair<OrderDeadlineDto, Performer> pair) {
        OrderDeadlineDto deadlineDto = pair.getFirst();
        Performer performer = pair.getSecond();

        String text = String.format(
                "Добрый день, %s!\nНапоминаем, что истекает время выполнения задания. Осталось - %s дней",
                performer.getUsername(),
                ChronoUnit.DAYS.between(LocalDate.now(), deadlineDto.deadline())
        );
        return new EmailNotification(
                performer.getEmail(),
                "Напоминание о задании",
                text
        );
    }
}
