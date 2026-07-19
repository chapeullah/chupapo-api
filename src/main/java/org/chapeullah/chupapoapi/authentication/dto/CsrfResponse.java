package org.chapeullah.chupapoapi.authentication.dto;

public record CsrfResponse(
        String headerName,
        String parameterName,
        String token) {}