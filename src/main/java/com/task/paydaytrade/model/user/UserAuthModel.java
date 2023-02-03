package com.task.paydaytrade.model.user;

import com.task.paydaytrade.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAuthModel {
    Long id;
    String email;
    String password;
    Boolean isActive;
    Set<RoleAuthModel> roles = new HashSet<>();

    public UserAuthModel(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.isActive = user.getIsActive();
        user.getRoles().forEach(role -> roles.add(new RoleAuthModel(role)));
    }
}