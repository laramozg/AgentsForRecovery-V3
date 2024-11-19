package org.example.sportsorder.mappers;

import org.example.sportsorder.controllers.order.dto.OrderDto;
import org.example.sportsorder.controllers.order.dto.OrderRequest;
import org.example.sportsorder.models.Order;
import org.example.sportsorder.services.CityService;
import org.example.sportsorder.services.MutilationService;
import org.example.sportsorder.services.VictimService;
import org.example.sportsorder.utils.SecurityContextMockUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.example.sportsorder.utils.Models.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


class OrderMapperTest {
    @Mock
    private CityService cityService;

    @Mock
    private VictimService victimService;

    @Mock
    private MutilationService mutilationService;

    private OrderMapper orderMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextMockUtil.mockSecurityContext();
        orderMapper = new OrderMapper(cityService, victimService, mutilationService);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testConvertToEntity() {
        OrderRequest request = ORDERREQUEST;

        when(cityService.find(request.cityId())).thenReturn(CITY);
        when(victimService.find(request.victimId())).thenReturn(VICTIM);
        when(mutilationService.findAllMutilationsById(request.mutilationIds())).thenReturn(List.of(MUTILATION));

        Order order = orderMapper.convertToEntity(request);

        assertEquals(request.cityId(), order.getCity().getId());
        assertEquals(request.victimId(), order.getVictim().getId());
        assertEquals(request.mutilationIds().size(), order.getOrderMutilations().size());
    }

    @Test
    void testConvertToDto() {
        ORDER.setOrderMutilations(List.of(ORDERMUTILATION));
        OrderDto dto = orderMapper.convertToDto(ORDER);

        assertEquals(ORDER.getId(), dto.id());
        assertEquals(ORDER.getUserId(), dto.userId());
        assertEquals(ORDER.getCity().getId(), dto.cityId());
        assertEquals(ORDER.getVictim().getId(), dto.victimId());
        assertEquals(ORDER.getOrderMutilations().size(), dto.mutilations().size());
    }
}
