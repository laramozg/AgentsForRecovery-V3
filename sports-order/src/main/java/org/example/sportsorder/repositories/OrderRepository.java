package org.example.sportsorder.repositories;

import org.example.sportsorder.models.Order;
import org.example.sportsorder.models.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findAllByStatus(OrderStatus orderStatus, Pageable pageable);
    Page<Order> findByUserId(UUID userId, Pageable pageable);
}
