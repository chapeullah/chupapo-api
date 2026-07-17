package org.chapeullah.chupapoapi.user.dto;

public record UpdateUserRequest(
        String username,
        String password,
        String roleName,
        Boolean enabled) {}