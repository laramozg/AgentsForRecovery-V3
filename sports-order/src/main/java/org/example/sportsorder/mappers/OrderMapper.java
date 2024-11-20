package org.example.sportsorder.mappers;

import lombok.AllArgsConstructor;
import org.example.sportsorder.controllers.mutilation.dto.MutilationDto;
import org.example.sportsorder.controllers.order.dto.OrderDto;
import org.example.sportsorder.controllers.order.dto.OrderRequest;
import org.example.sportsorder.models.Mutilation;
import org.example.sportsorder.models.Order;
import org.example.sportsorder.models.OrderMutilation;
import org.example.sportsorder.services.CityService;
import org.example.sportsorder.services.MutilationService;
import org.example.sportsorder.services.VictimService;
import org.example.sportsorder.util.SecurityContext;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Component
public class OrderMapper {
    private final CityService cityService;
    private final VictimService victimService;
    private final MutilationService mutilationService;

    public Order convertToEntity(OrderRequest orderRequest){
        List<Mutilation> mutilations = (mutilationService.findAllMutilationsById(orderRequest.mutilationIds()));
        Order order = Order.builder()
                .userId(SecurityContext.getAuthorizedUserId())
                .city(cityService.find(orderRequest.cityId()))
                .victim(victimService.find(orderRequest.victimId()))
                .deadline(orderRequest.deadline()).build();
        List<OrderMutilation> orderMutilations = mutilations.stream()
                .map(mutilation -> new OrderMutilation(order, mutilation))
                .toList();
        order.setOrderMutilations(orderMutilations);
        return order;
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

    private MutilationDto toMutilationDto(OrderMutilation orderMutilation) {
        Mutilation mutilation = orderMutilation.getMutilation();
        return new MutilationDto(
                mutilation.getId(),
                mutilation.getType(),
                mutilation.getPrice()
        );
    }
}
