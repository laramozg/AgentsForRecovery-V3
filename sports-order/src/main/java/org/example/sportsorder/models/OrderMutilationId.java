package org.example.sportsorder.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class OrderMutilationId implements Serializable {
    private UUID order;
    private UUID mutilation;
}