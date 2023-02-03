package com.task.paydaytrade.service.impl;

import com.task.paydaytrade.repository.OrderRepository;
import com.task.paydaytrade.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceImplTest {
    @Mock
    UserService userService;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void shouldPlaceOrder() {
        when(userService.findByEmail("test@gmail.com")).thenReturn(MockData.getUser());

        var orderRsModel = orderService.placeOrder("test@gmail.com", MockData.getOrderRqModel());
        assertEquals(orderRsModel.price(), MockData.getOrderRqModel().price());
        verify(userService, times(1)).findByEmail(any());
        verify(orderRepository, times(1)).save(any());
    }
}