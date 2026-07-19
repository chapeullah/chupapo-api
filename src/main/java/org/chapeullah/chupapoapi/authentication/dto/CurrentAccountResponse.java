package org.chapeullah.chupapoapi.authentication.dto;

import java.util.Set;

public record CurrentAccountResponse(
        String username,
        Set<String> authorities) {}