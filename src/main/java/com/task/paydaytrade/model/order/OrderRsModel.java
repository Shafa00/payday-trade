package com.task.paydaytrade.model.order;

import com.task.paydaytrade.utility.OrderType;

import java.math.BigDecimal;

public record OrderRsModel(String externalId, BigDecimal price, String stock, OrderType orderType, Boolean isFilled) {
}
