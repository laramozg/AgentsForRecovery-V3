package org.example.sportsorder.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsorder.exceptions.ErrorCode;
import org.example.sportsorder.exceptions.InternalException;
import org.example.sportsorder.models.Order;
import org.example.sportsorder.models.enums.OrderStatus;
import org.example.sportsorder.repositories.OrderRepository;
import org.example.sportsorder.util.SecurityContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public UUID createOrder(Order order) {
        order.setStatus(OrderStatus.WAITING);
        return orderRepository.save(order).getId();
    }

    public Page<Order> findOrdersWithStatusWait(Pageable pageable) {
        return orderRepository.findAllByStatus(OrderStatus.WAITING, pageable);
    }

    public Page<Order> findOrdersByUsername(Pageable pageable) {
        return orderRepository.findByUserId(SecurityContext.getAuthorizedUserId(), pageable);
    }

    public Order findOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new InternalException(HttpStatus.NOT_FOUND, ErrorCode.ORDER_NOT_FOUND));
    }

    public void deleteOrder(UUID id) {
        orderRepository.deleteById(id);
    }
}
