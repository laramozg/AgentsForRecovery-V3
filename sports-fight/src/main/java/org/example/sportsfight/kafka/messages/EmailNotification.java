package org.example.sportsfight.kafka.messages;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailNotification {

    private final UUID id = UUID.randomUUID();
    private String email;
    private String title;
    private String text;
    private final LocalDateTime localDateTime = LocalDateTime.now();

}