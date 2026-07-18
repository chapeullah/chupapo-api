package org.chapeullah.chupapoapi.iam.account.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.iam.account.application.AccountService;
import org.chapeullah.chupapoapi.iam.account.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * POST   /api/accounts
 * GET    /api/accounts/{id}
 * PUT    /api/accounts/{id}/username
 * PUT    /api/accounts/{id}/role
 * PUT    /api/accounts/{id}/password
 * POST   /api/accounts/{id}/enable
 * POST   /api/accounts/{id}/disable
 * DELETE /api/accounts/{id}
 */
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(
            @Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getAccount(@PathVariable(name = "id") Long accountId) {
        return accountService.getAccount(accountId);
    }

    @PutMapping("/{id}/username")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse updateAccountUsername(
            @PathVariable(name = "id") Long accountId,
            @Valid @RequestBody UpdateAccountUsernameRequest request) {
        return accountService.updateAccountUsername(accountId, request);
    }

    @PutMapping("/{id}/role")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse updateAccountRole(
            @PathVariable(name = "id") Long accountId,
            @Valid @RequestBody UpdateAccountRoleRequest request) {
        return accountService.updateAccountRole(accountId, request);
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse updateAccountPassword(
            @PathVariable(name = "id") Long accountId,
            @Valid @RequestBody UpdateAccountPasswordRequest request) {
        return accountService.updateAccountPassword(accountId, request);
    }

    @PostMapping("/{id}/enable")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse enableAccount(@PathVariable(name = "id") Long accountId) {
        return accountService.enableAccount(accountId);
    }

    @PostMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse disableAccount(@PathVariable(name = "id") Long accountId) {
        return accountService.disableAccount(accountId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable(name = "id") Long accountId) {
        accountService.deleteAccount(accountId);
    }

}
