package org.example.sportsgateway.dto;

import java.util.List;
import java.util.UUID;

public record AuthorizationDetails(UUID id,
                                   String username,
                                   List<String> roles) {


}
