package org.example.sportsorder.mappers;

import lombok.AllArgsConstructor;
import org.example.sportsorder.controllers.mutilation.dto.MutilationDto;
import org.example.sportsorder.controllers.order.dto.OrderDeadlineResponse;
import org.example.sportsorder.controllers.order.dto.OrderDto;
import org.example.sportsorder.controllers.order.dto.OrderRequest;
import org.example.sportsorder.controllers.order.dto.OrderUpdateResponse;
import org.example.sportsorder.models.Mutilation;
import org.example.sportsorder.models.Order;
import org.example.sportsorder.models.OrderMutilation;
import org.example.sportsorder.models.enums.OrderStatus;
import org.example.sportsorder.services.CityService;
import org.example.sportsorder.services.VictimService;
import org.example.sportsorder.util.SecurityContext;
import org.springframework.stereotype.Component;

import java.util.Collections;

@AllArgsConstructor
@Component
public class OrderMapper {
    private final CityService cityService;
    private final VictimService victimService;

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

    public OrderDeadlineResponse convertToDeadline(Order order){
        return new OrderDeadlineResponse(order.getId(), order.getDeadline());
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
