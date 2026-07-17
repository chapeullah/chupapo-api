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
    public AccountResponse getAccount(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PutMapping("/{id}/username")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse renameAccount(
            @PathVariable Long id,
            @Valid @RequestBody RenameAccountRequest request) {
        return accountService.renameAccount(id, request);
    }

    @PutMapping("/{id}/role")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse assignRole(
            @PathVariable Long id,
            @Valid @RequestBody AssignAccountRoleRequest request) {
        return accountService.assignRole(id, request);
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse resetPassword(
            @PathVariable Long id,
            @Valid @RequestBody ResetPasswordRequest request) {
        return accountService.resetPassword(id, request);
    }

    @PostMapping("/{id}/enable")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse enableAccount(@PathVariable Long id) {
        return accountService.enableAccount(id);
    }

    @PostMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse disableAccount(@PathVariable Long id) {
        return accountService.disableAccount(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

}
