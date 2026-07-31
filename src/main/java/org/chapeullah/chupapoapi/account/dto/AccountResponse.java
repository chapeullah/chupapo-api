package org.chapeullah.chupapoapi.account.dto;

import org.chapeullah.chupapoapi.account.model.Account;

import java.time.Instant;

public record AccountResponse(
        Long id,
        String username,
        String roleName,
        boolean enabled,
        Instant createdAt,
        Instant updatedAt) {

    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getUsername(),
                account.getRole().getName(),
                account.isEnabled(),
                account.getCreatedAt(),
                account.getUpdatedAt());
    }

}