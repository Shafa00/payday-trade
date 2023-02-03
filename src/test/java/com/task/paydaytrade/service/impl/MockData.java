package com.task.paydaytrade.service.impl;

import com.task.paydaytrade.entity.Account;
import com.task.paydaytrade.entity.User;
import com.task.paydaytrade.model.account.AccountRqModel;
import com.task.paydaytrade.model.order.OrderRqModel;
import com.task.paydaytrade.model.stock.StockRsModel;
import com.task.paydaytrade.model.user.SignupRqModel;
import com.task.paydaytrade.utility.OrderType;

import java.math.BigDecimal;
import java.util.List;

public class MockData {
    public static SignupRqModel getSignupRqModel() {
        return new SignupRqModel("test", "test@gmail.com", "test");
    }

    public static User getUser() {
        return User.builder()
                .id(1L)
                .email("test@gmail.com")
                .roles(List.of())
                .build();
    }

    public static StockRsModel getStockRsModel() {
        return new StockRsModel("test", BigDecimal.TEN);
    }

    public static OrderRqModel getOrderRqModel() {
        return new OrderRqModel(BigDecimal.TEN, "test", OrderType.BUY);
    }

    public static AccountRqModel getAccountRqModel() {
        return new AccountRqModel(BigDecimal.TEN);
    }

    public static Account getAccount() {
        return Account.builder()
                .id(1L)
                .amount(BigDecimal.ZERO)
                .build();
    }
}
