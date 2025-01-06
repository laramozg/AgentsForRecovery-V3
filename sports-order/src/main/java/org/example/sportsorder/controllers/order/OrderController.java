package org.example.sportsorder.controllers.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sportsorder.controllers.mutilation.dto.MutilationResponse;
import org.example.sportsorder.controllers.order.dto.OrderDeadlineResponse;
import org.example.sportsorder.controllers.order.dto.OrderDto;
import org.example.sportsorder.controllers.order.dto.OrderRequest;
import org.example.sportsorder.controllers.order.dto.OrderResponse;
import org.example.sportsorder.controllers.order.dto.OrderUpdateResponse;
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

    @Operation(summary = "Создание заказа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Заказ создан", content = @Content(schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request) {
        logger.trace("Create order request: {}", request);
        UUID order = orderService.createOrder(orderMapper.convertToEntity(request), request.mutilationIds());
        return new OrderResponse(order);
    }

    @Operation(summary = "Поиск заказов по статусу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Заказы найдены", content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDto> findOrdersWithStatusWait(
            @Schema(hidden = true)@PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable
    ) {
        logger.trace("Find orders with status wait");
        return orderService.findOrdersWithStatusWait(pageable).map(orderMapper::convertToDto);
    }

    @Operation(summary = "Поиск заказов по пользователю")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Заказы найдены", content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDto> findOrdersByUsername(
            @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) Pageable pageable
    ) {
        logger.trace("Find orders by username");
        return orderService.findOrdersByUsername(pageable).map(orderMapper::convertToDto);
    }

    @Operation(summary = "Поиск заказов по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Заказы найдены", content = @Content(schema = @Schema(implementation = OrderDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDeadlineResponse findOrdersById(@PathVariable UUID id) {
        logger.trace("Find orders by id");
        return orderMapper.convertToDeadline(orderService.find(id));
    }

    @Operation(summary = "Удаление заказа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Заказ удален", content = @Content(schema = @Schema)),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'CUSTOMER')")
    public void deleteOrder(@PathVariable UUID id) {
        logger.trace("Delete order with id: {}", id);
        orderService.deleteOrder(id);
    }

    @Operation(summary = "Обновление статуса заказа")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Статус заказ обновлен", content = @Content(schema = @Schema(implementation = OrderUpdateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "403", description = "Не доступно", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Сервис не доступен", content = @Content(schema = @Schema(implementation = Error.class)))
    })
    @PostMapping("/{id}/{status}")
    @ResponseStatus(HttpStatus.OK)
    public OrderUpdateResponse updateStatus(@PathVariable UUID id, @PathVariable String status){
        logger.trace("Update order with status: {}", status);
        return orderMapper.convert(orderService.updateStatus(id, status));
    }

}