package com.task.paydaytrade.model.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record SignupRqModel(@NotBlank @Size(max = 20) String name,
                            @NotBlank(message = "Fill email") @Size(max = 50)
                            @Email(message = "Invalid email format") String email,
                            @NotBlank @Size(max = 20, min = 6) String password) {
}
