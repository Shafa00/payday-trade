package com.task.paydaytrade.model.account;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AccountRqModel(@NotNull BigDecimal amount) {
}
