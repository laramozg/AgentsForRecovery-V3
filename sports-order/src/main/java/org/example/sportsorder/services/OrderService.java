package org.example.sportsorder.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsorder.exceptions.ErrorCode;
import org.example.sportsorder.exceptions.InternalException;
import org.example.sportsorder.models.Order;
import org.example.sportsorder.models.enums.OrderStatus;
import org.example.sportsorder.repositories.OrderRepository;
import org.example.sportsorder.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public UUID createOrder(Order order) {
        logger.info("Creating order {}", order);
        order.setStatus(OrderStatus.WAITING);
        return orderRepository.save(order).getId();
    }

    public Page<Order> findOrdersWithStatusWait(Pageable pageable) {
        logger.info("Finding orders with status waiting {}", pageable);
        return orderRepository.findAllByStatus(OrderStatus.WAITING, pageable);
    }

    public Page<Order> findOrdersByUsername(Pageable pageable) {
        logger.info("Finding orders by username {}", pageable);
        return orderRepository.findByUserId(SecurityContext.getAuthorizedUserId(), pageable);
    }

    public Order find(UUID id) {
        logger.info("Finding order by id {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new InternalException(HttpStatus.NOT_FOUND, ErrorCode.ORDER_NOT_FOUND));
    }

    public void deleteOrder(UUID id) {
        logger.info("Deleting order by id {}", id);
        orderRepository.deleteById(id);
    }
}
