package org.example.sportsfight.services;

import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.sportsfight.utils.Models.EMAIL;
import static org.example.sportsfight.utils.Models.ORDER_DEADLINE_DTO;
import static org.example.sportsfight.utils.Models.PERFORMER;

public class TemplateServiceTest {

    private final TemplateService templateService = new TemplateService();

    @Test
    public void convertToEmailMessageShouldExecuteSuccessfully() {
        var pair = Pair.of(ORDER_DEADLINE_DTO(), PERFORMER());

        var email = templateService.convertToEmailMessage(pair);

        assertThat(email.getEmail()).isEqualTo(EMAIL);
        assertThat(email.getTitle()).isEqualTo("Напоминание о задании");
    }
}
