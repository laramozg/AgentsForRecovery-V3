package org.example.sportsgateway.dto;

import java.util.UUID;

public class AuthorizationDetails {
    private final UUID id;
    private final String username;
    private final String role;

    public AuthorizationDetails(UUID id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }


    public String getRole() {
        return role;
    }

}
