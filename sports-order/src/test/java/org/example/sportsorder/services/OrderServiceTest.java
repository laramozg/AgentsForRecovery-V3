package org.example.sportsorder.services;

import org.example.sportsorder.exceptions.InternalException;
import org.example.sportsorder.models.Order;
import org.example.sportsorder.models.enums.OrderStatus;
import org.example.sportsorder.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.sportsorder.utils.Models.ORDER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private final Order testOrder = ORDER;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void shouldCreateOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        UUID createdId = orderService.createOrder(testOrder);

        assertEquals(testOrder.getId(), createdId);
    }

    @Test
    void shouldFindOrdersWithStatusWait() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Order> orders = new PageImpl<>(List.of(testOrder));
        when(orderRepository.findAllByStatus(OrderStatus.WAITING, pageable)).thenReturn(orders);

        Page<Order> foundOrders = orderService.findOrdersWithStatusWait(pageable);

        assertEquals(1, foundOrders.getTotalElements());
    }

    @Test
    void shouldThrowWhenOrderNotFound() {
        UUID id = UUID.randomUUID();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(InternalException.class, () -> orderService.find(id));
    }
}
