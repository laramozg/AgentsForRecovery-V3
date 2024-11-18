package org.example.sportsfight.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "performers")
public class Performer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "passport_series_number", nullable = false)
    private String passportSeriesNumber;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false)
    private Double height;

    @Column(columnDefinition = "integer default 0.0", nullable = false)
    private Double rating;

    @Column(name = "completed_orders", columnDefinition = "integer default 0", nullable = false)
    private Integer completedOrders;


}
