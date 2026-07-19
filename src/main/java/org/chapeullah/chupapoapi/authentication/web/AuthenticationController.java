package org.chapeullah.chupapoapi.authentication.web;

import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.authentication.dto.CsrfResponse;
import org.chapeullah.chupapoapi.authentication.dto.CurrentAccountResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @GetMapping("/csrf")
    public CsrfResponse csrf(CsrfToken csrfToken) {
        return new CsrfResponse(
                csrfToken.getHeaderName(),
                csrfToken.getParameterName(),
                csrfToken.getToken());
    }

    @GetMapping("/me")
    public CurrentAccountResponse currentAccount(Authentication authentication) {
        @SuppressWarnings("DataFlowIssue")
        Set<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toUnmodifiableSet());
        return new CurrentAccountResponse(authentication.getName(), authorities);
    }

}
