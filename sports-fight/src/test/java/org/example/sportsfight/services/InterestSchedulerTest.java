package org.example.sportsfight.services;

import java.util.ArrayList;
import java.util.List;

import org.example.sportsfight.kafka.KafkaNotificationProducer;
import org.example.sportsfight.models.Performer;
import org.example.sportsfight.services.clients.dto.OrderDeadlineDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import static org.example.sportsfight.utils.Models.EMAIL_NOTIFICATION;
import static org.example.sportsfight.utils.Models.ORDER_DEADLINE_DTO;
import static org.example.sportsfight.utils.Models.PERFORMER;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InterestSchedulerTest {

    @Mock
    private FightService fightService;

    @Mock
    private TemplateService templateService;

    @Mock
    private KafkaNotificationProducer kafkaNotificationProducer;

    @InjectMocks
    private InterestScheduler interestScheduler;

    @Test
    void calculateMountInterestShouldExecuteSuccessfully() {
        var email = EMAIL_NOTIFICATION();
        var pairs = List.of( Pair.of(ORDER_DEADLINE_DTO(), PERFORMER()));

        when(fightService.calculateDeadline()).thenReturn(pairs);
        when(templateService.convertToEmailMessage(Mockito.any())).thenReturn(email);

        interestScheduler.fightDeadlineAndNotify();
    }
}
