package org.chapeullah.chupapoapi.iam.authentication.dto;

public record CsrfResponse(
        String headerName,
        String parameterName,
        String token) {}