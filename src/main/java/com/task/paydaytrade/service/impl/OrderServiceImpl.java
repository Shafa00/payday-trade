package com.task.paydaytrade.service.impl;

import com.task.paydaytrade.entity.Order;
import com.task.paydaytrade.model.order.OrderRqModel;
import com.task.paydaytrade.model.order.OrderRsModel;
import com.task.paydaytrade.repository.OrderRepository;
import com.task.paydaytrade.service.OrderService;
import com.task.paydaytrade.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final UserService userService;
    private final OrderRepository orderRepository;

    @Override
    public OrderRsModel placeOrder(String email, OrderRqModel orderRqModel) {
        var order = Order.builder()
                .externalId(UUID.randomUUID().toString())
                .stock(orderRqModel.stock())
                .price(orderRqModel.price())
                .orderType(orderRqModel.orderType())
                .user(userService.findByEmail(email))
                .isFilled(Boolean.FALSE)
                .build();
        orderRepository.save(order);

        return new OrderRsModel(order.getExternalId(), order.getPrice(), orderRqModel.stock(),
                order.getOrderType(), order.getIsFilled());
    }
}
