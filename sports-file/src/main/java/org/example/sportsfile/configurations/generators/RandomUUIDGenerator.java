package org.example.sportsfile.configurations.generators;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RandomUUIDGenerator implements IdGenerator {

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}