package org.example.sportsorder.controllers.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sportsorder.controllers.order.dto.OrderDto;
import org.example.sportsorder.controllers.order.dto.OrderRequest;
import org.example.sportsorder.controllers.order.dto.OrderResponse;
import org.example.sportsorder.mappers.OrderMapper;
import org.example.sportsorder.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    public static final int DEFAULT_PAGE_SIZE = 50;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request) {
        logger.trace("Create order request: {}", request);
        UUID order = orderService.createOrder(orderMapper.convertToEntity(request));
        return new OrderResponse(order);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDto> findOrdersWithStatusWait(
            @Schema(hidden = true)@PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable
    ) {
        logger.trace("Find orders with status wait");
        return orderService.findOrdersWithStatusWait(pageable).map(orderMapper::convertToDto);
    }

    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDto> findOrdersByUsername(
            @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable
    ) {
        logger.trace("Find orders by username");
        return orderService.findOrdersByUsername(pageable).map(orderMapper::convertToDto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'CUSTOMER')")
    public void deleteOrder(@PathVariable UUID id) {
        logger.trace("Delete order with id: {}", id);
        orderService.deleteOrder(id);
    }

}