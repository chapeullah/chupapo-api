package org.chapeullah.chupapoapi.iam.auth.dto;

public record CsrfResponse(
        String headerName,
        String parameterName,
        String token) {}
