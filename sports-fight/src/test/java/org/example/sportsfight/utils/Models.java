package org.example.sportsfight.utils;

import org.example.sportsfight.controllers.fight.dto.FightRequest;
import org.example.sportsfight.controllers.performer.dto.PerformerRequest;
import org.example.sportsfight.models.Fight;
import org.example.sportsfight.models.Performer;
import org.example.sportsfight.models.enums.FightStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.UUID;

public class Models {
    public static final String USERNAME = "username";
    public static final UUID USER_ID = UUID.fromString("abf3785d-089d-42b2-a6cd-53cdb3d86752");
    public static final List<SimpleGrantedAuthority> USER_ROLES = List.of(new SimpleGrantedAuthority("EXECUTOR"));

    public static Performer PERFORMER() {
        return Performer.builder()
                .id(UUID.randomUUID())
                .username(USERNAME)
                .passportSeriesNumber("123123")
                .weight(100.0)
                .height(180.0)
                .rating(0.0)
                .completedOrders(0)
                .build();
    }

    public static PerformerRequest PERFORMER_REQUEST() {
        return new PerformerRequest("123123",100.0,180.0);
    }

    public static Fight FIGHT() {
        return Fight.builder()
                .id(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .performer(PERFORMER())
                .status(FightStatus.PENDING)
                .build();
    }

    public static FightRequest FIGHT_REQUEST() {
        return new FightRequest(PERFORMER().getId(), FIGHT().getOrderId());
    }

}
