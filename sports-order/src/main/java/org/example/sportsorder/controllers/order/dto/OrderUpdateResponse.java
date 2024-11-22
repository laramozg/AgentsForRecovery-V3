package org.example.sportsorder.controllers.order.dto;

import java.util.UUID;

public record OrderUpdateResponse(UUID id,
                                  String status) {
}
