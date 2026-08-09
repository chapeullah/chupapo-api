package org.chapeullah.chupapoapi.account.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.account.application.AccountService;
import org.chapeullah.chupapoapi.account.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @PreAuthorize("hasAuthority('ACCOUNTS_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(
            @Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ACCOUNTS_READ')")
    @ResponseStatus(HttpStatus.OK)
    public Page<AccountResponse> getAccounts(
            @PageableDefault(
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC)
            Pageable pageable) {
        return accountService.getAccounts(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ACCOUNTS_READ')")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccount(
            @Positive(message = "Account ID must be positive")
            @PathVariable(name = "id") Long accountId) {
        return accountService.getAccount(accountId);
    }

    @PutMapping("/{id}/username")
    @PreAuthorize("hasAuthority('ACCOUNTS_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse updateAccountUsername(
            @Positive(message = "Account ID must be positive")
            @PathVariable(name = "id") Long accountId,
            @Valid @RequestBody UpdateAccountUsernameRequest request) {
        return accountService.updateAccountUsername(accountId, request);
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasAuthority('ACCOUNTS_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse updateAccountRole(
            @Positive(message = "Account ID must be positive")
            @PathVariable(name = "id") Long accountId,
            @Valid @RequestBody UpdateAccountRoleRequest request) {
        return accountService.updateAccountRole(accountId, request);
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasAuthority('ACCOUNTS_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse updateAccountPassword(
            @Positive(message = "Account ID must be positive")
            @PathVariable(name = "id") Long accountId,
            @Valid @RequestBody UpdateAccountPasswordRequest request) {
        return accountService.updateAccountPassword(accountId, request);
    }

    @PostMapping("/{id}/enable")
    @PreAuthorize("hasAuthority('ACCOUNTS_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse enableAccount(
            @Positive(message = "Account ID must be positive")
            @PathVariable(name = "id") Long accountId) {
        return accountService.enableAccount(accountId);
    }

    @PostMapping("/{id}/disable")
    @PreAuthorize("hasAuthority('ACCOUNTS_UPDATE')")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse disableAccount(
            @Positive(message = "Account ID must be positive")
            @PathVariable(name = "id") Long accountId) {
        return accountService.disableAccount(accountId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ACCOUNTS_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(
            @Positive(message = "Account ID must be positive")
            @PathVariable(name = "id") Long accountId) {
        accountService.deleteAccount(accountId);
    }

}
