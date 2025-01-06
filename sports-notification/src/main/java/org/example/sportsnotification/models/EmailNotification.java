package org.example.sportsnotification.models;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmailNotification(
        UUID id,
        String email,
        String title,
        String text,
        LocalDateTime localDateTime
) {
}
