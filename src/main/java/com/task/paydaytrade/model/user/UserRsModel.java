package com.task.paydaytrade.model.user;

import java.util.UUID;

public record UserRsModel(UUID userId,
                          String name,
                          String surname,
                          String email,
                          UUID organizationId) {
}
