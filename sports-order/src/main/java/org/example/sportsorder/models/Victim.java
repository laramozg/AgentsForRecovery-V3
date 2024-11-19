package org.example.sportsorder.models;

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
@Table(name = "victims")
public class Victim {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "workplace")
    private String workplace;

    @Column(name = "position")
    private String position;

    @Column(name = "residence")
    private String residence;

    @Column(name = "phone")
    private String phone;

    @Column(columnDefinition = "TEXT", name = "description")
    private String description;
}