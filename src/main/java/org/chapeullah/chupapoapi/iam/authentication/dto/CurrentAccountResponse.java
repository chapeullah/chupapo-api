package org.chapeullah.chupapoapi.iam.authentication.dto;

import java.util.Set;

public record CurrentAccountResponse(
        String username,
        Set<String> authorities) {}