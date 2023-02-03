package com.task.paydaytrade.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserLoginModel(@NotBlank @Size(max = 20) @Email(message = "email has invalid format") String email,
                             @NotBlank @Size(max = 20, min = 6) String password) {
}