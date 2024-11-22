package org.example.sportsorder.mappers;

import lombok.AllArgsConstructor;
import org.example.sportsorder.controllers.mutilation.dto.MutilationDto;
import org.example.sportsorder.controllers.order.dto.OrderDto;
import org.example.sportsorder.controllers.order.dto.OrderRequest;
import org.example.sportsorder.controllers.order.dto.OrderUpdateResponse;
import org.example.sportsorder.models.Mutilation;
import org.example.sportsorder.models.Order;
import org.example.sportsorder.models.OrderMutilation;
import org.example.sportsorder.models.enums.OrderStatus;
import org.example.sportsorder.services.CityService;
import org.example.sportsorder.services.MutilationService;
import org.example.sportsorder.services.VictimService;
import org.example.sportsorder.util.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Component
public class OrderMapper {
    private final CityService cityService;
    private final VictimService victimService;
    private final MutilationService mutilationService;
    private final Logger logger = LoggerFactory.getLogger(OrderMapper.class);

    public Order convertToEntity(OrderRequest orderRequest){
        return Order.builder()
                .userId(SecurityContext.getAuthorizedUserId())
                .city(cityService.find(orderRequest.cityId()))
                .victim(victimService.find(orderRequest.victimId()))
                .deadline(orderRequest.deadline())
                .status(OrderStatus.WAITING).build();
    }

    public OrderDto convertToDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getUserId(),
                order.getCity().getId(),
                order.getVictim().getId(),
                order.getDeadline(),
                order.getStatus().toString(),
                order.getOrderMutilations() != null ?
                        order.getOrderMutilations().stream()
                                .map(this::toMutilationDto)
                                .toList() :
                        Collections.emptyList()
        );
    }

    public OrderUpdateResponse convert(Order order) {
        return new OrderUpdateResponse(order.getId(), order.getStatus().toString());
    }

    private MutilationDto toMutilationDto(OrderMutilation orderMutilation) {
        Mutilation mutilation = orderMutilation.getMutilation();
        return new MutilationDto(
                mutilation.getId(),
                mutilation.getType(),
                mutilation.getPrice()
        );
    }
}
