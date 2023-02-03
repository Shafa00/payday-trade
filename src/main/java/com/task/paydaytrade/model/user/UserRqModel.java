package com.task.paydaytrade.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UserRqModel(@NotBlank @Size(max = 20, min = 6) String password,
                          @NotBlank @Size(max = 20) String name,
                          @Size(max = 50) String surname,
                          @NotBlank @Size(max = 50) @Email(message = "email has invalid format") String email) {
}
