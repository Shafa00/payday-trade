package com.task.paydaytrade.service;

import com.task.paydaytrade.model.order.OrderRqModel;
import com.task.paydaytrade.model.order.OrderRsModel;

public interface OrderService {
    OrderRsModel placeOrder(String email, OrderRqModel orderRqModel);
}
