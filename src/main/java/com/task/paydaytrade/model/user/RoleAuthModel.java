package com.task.paydaytrade.model.user;

import com.task.paydaytrade.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleAuthModel {
    Long id;
    String name;

    public RoleAuthModel(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
