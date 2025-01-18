package org.example.sportsorder.utils;

import org.example.sportsorder.controllers.city.dto.CityRequest;
import org.example.sportsorder.controllers.mutilation.dto.MutilationRequest;
import org.example.sportsorder.controllers.order.dto.OrderRequest;
import org.example.sportsorder.controllers.victim.dto.VictimRequest;
import org.example.sportsorder.models.*;
import org.example.sportsorder.models.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Models {

    public static final UUID USER_ID = UUID.randomUUID();
    public static final String USERNAME = "username";
    public static final String ROLE_SUPERVISOR = "SUPERVISOR";
    public static final String ROLE_WORKER = "EXECUTOR";
    public static final String ROLE_CUSTOMER = "CUSTOMER";
    private static final LocalDate now = LocalDate.now();

    public static City CITY = City.builder()
            .id(UUID.randomUUID())
            .name("New York")
            .region("NY").build();

    public static CityRequest CITYREQUEST = new CityRequest(
            "New York", "NY"
    );

    public static Mutilation MUTILATION = Mutilation.builder()
            .id(UUID.randomUUID())
            .type("Break a leg")
            .price(100000L).build();

    public static MutilationRequest MUTILATIONREQUEST = new MutilationRequest(
            "Break a leg", 100000L
    );

    public static Victim VICTIM = Victim.builder()
            .id(UUID.randomUUID())
            .firstName("John")
            .lastName("Doe")
            .workplace("Company")
            .position("Manager")
            .residence("City")
            .phone("123456789")
            .description("No description").build();

    public static VictimRequest VICTIMREQUEST = new VictimRequest(
            "Jame", "Johny", "Company", "Manager",
            "City", "123456789", "Nodescription"
    );

    public static Order ORDER = Order.builder()
            .id(UUID.randomUUID())
            .userId(UUID.randomUUID())
            .city(CITY)
            .victim(VICTIM)
            .deadline(now)
            .status(OrderStatus.WAITING)
            .build();

    public static OrderRequest ORDERREQUEST = new OrderRequest(
            CITY.getId(), VICTIM.getId(), now,  List.of(MUTILATION.getId())
    );

    public static OrderMutilation ORDERMUTILATION = OrderMutilation.builder()
            .mutilation(MUTILATION)
            .order(ORDER).build();
}
