package org.example.sportsorder.services;

import lombok.RequiredArgsConstructor;
import org.example.sportsorder.controllers.order.dto.OrderRequest;
import org.example.sportsorder.exceptions.ErrorCode;
import org.example.sportsorder.exceptions.InternalException;
import org.example.sportsorder.models.Mutilation;
import org.example.sportsorder.models.Order;
import org.example.sportsorder.models.OrderMutilation;
import org.example.sportsorder.models.enums.OrderStatus;
import org.example.sportsorder.repositories.MutilationRepository;
import org.example.sportsorder.repositories.OrderRepository;
import org.example.sportsorder.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MutilationService mutilationService;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Transactional
    public UUID createOrder(Order order, List<UUID> mutilationIds) {
        List<Mutilation> mutilations = (mutilationService.findAllMutilationsById(mutilationIds));
        List<OrderMutilation> orderMutilations = mutilations.stream()
                .map(mutilation -> new OrderMutilation(order, mutilation))
                .toList();
        order.setOrderMutilations(orderMutilations);
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

    public Order updateStatus(UUID id, String status) {
        logger.info("Updating status order by id {}", id);
        Order order = find(id);
        order.setStatus(OrderStatus.valueOf(status));
        return order;
    }
}
