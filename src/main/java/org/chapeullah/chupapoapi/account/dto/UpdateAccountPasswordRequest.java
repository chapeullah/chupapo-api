package org.chapeullah.chupapoapi.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateAccountPasswordRequest(
        @NotBlank(message = "Password must not be blank")
        @Size(
                min = 15,
                max = 128,
                message = "Password must be between 15 and 128 characters")
        String password) {}