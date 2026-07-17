package org.chapeullah.chupapoapi.iam.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @NotBlank(message = "Password must not be blank")
        @Size(
                min = 12,
                max = 128,
                message = "Password must be between 12 and 128 characters")
        String password) {}