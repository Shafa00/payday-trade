package com.task.paydaytrade.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {
    BUY("BUY"),
    SELL("SELL");

    private final String roleName;

}
