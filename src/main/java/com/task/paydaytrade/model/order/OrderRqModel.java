package com.task.paydaytrade.model.order;

import com.task.paydaytrade.utility.OrderType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record OrderRqModel(@NotNull BigDecimal price, @NotBlank String stock, @NotNull OrderType orderType) {
}
