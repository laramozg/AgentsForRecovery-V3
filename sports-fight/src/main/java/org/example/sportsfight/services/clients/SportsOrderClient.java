package org.example.sportsfight.services.clients;

import feign.Headers;
import org.example.sportsfight.configurations.feign.FeignConfiguration;
import org.example.sportsfight.services.clients.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

@FeignClient(value = "${spring.cloud.openfeign.sports-order.name}", configuration = FeignConfiguration.class)
public interface SportsOrderClient {

    @Headers("Content-Type: application/json")
    @RequestMapping(method = RequestMethod.POST, value = "/orders/{id}/{status}")
    OrderDto updateStatusOrder(@PathVariable("id") UUID id, @PathVariable("status") String status);

}
