package org.chapeullah.chupapoapi.iam.auth.dto;

import java.util.Set;

public record CurrentAccountResponse(
        String username,
        Set<String> authorities) {}
