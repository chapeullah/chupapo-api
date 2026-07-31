package org.chapeullah.chupapoapi.account.application;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.account.dto.*;
import org.chapeullah.chupapoapi.authorization.exception.RoleNotFoundException;
import org.chapeullah.chupapoapi.authorization.model.Role;
import org.chapeullah.chupapoapi.authorization.repository.RoleRepository;
import org.chapeullah.chupapoapi.account.exception.AccountAlreadyExistsException;
import org.chapeullah.chupapoapi.account.exception.AccountNotFoundException;
import org.chapeullah.chupapoapi.account.model.Account;
import org.chapeullah.chupapoapi.account.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    public AccountResponse createAccount(@Valid CreateAccountRequest request) {
        if (accountRepository.existsByUsername(request.username()))
            throw new AccountAlreadyExistsException(request.username());
        Account account = new Account(
                request.username(),
                passwordEncoder.encode(request.password()),
                findRole(request.roleName()));
        return AccountResponse.from(accountRepository.saveAndFlush(account));
    }

    @Transactional(readOnly = true)
    public Page<AccountResponse> getAccounts(@NotNull Pageable pageable) {
        return accountRepository.findAll(pageable).map(AccountResponse::from);
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccount(@NotNull @Positive Long accountId) {
        return AccountResponse.from(findAccount(accountId));
    }

    @Transactional
    public AccountResponse updateAccountUsername(
            @NotNull @Positive Long accountId,
            @Valid UpdateAccountUsernameRequest request) {
        Account account = findAccount(accountId);
        if (!account.getUsername().equals(request.username())
                && accountRepository.existsByUsername(request.username()))
            throw new AccountAlreadyExistsException(request.username());
        account.setUsername(request.username());
        return AccountResponse.from(accountRepository.saveAndFlush(account));
    }

    @Transactional
    public AccountResponse updateAccountRole(
            @NotNull @Positive Long accountId,
            @Valid UpdateAccountRoleRequest request) {
        Account account = findAccount(accountId);
        account.setRole(findRole(request.roleId()));
        return AccountResponse.from(accountRepository.saveAndFlush(account));
    }

    @Transactional
    public AccountResponse updateAccountPassword(
            @NotNull @Positive Long accountId,
            @Valid UpdateAccountPasswordRequest request) {
        Account account = findAccount(accountId);
        account.setPasswordHash(passwordEncoder.encode(request.password()));
        return AccountResponse.from(accountRepository.saveAndFlush(account));
    }

    @Transactional
    public AccountResponse enableAccount(@NotNull @Positive Long accountId) {
        Account account = findAccount(accountId);
        account.setEnabled(true);
        return AccountResponse.from(accountRepository.saveAndFlush(account));
    }

    @Transactional
    public AccountResponse disableAccount(@NotNull @Positive Long accountId) {
        Account account = findAccount(accountId);
        account.setEnabled(false);
        return AccountResponse.from(accountRepository.saveAndFlush(account));
    }

    @Transactional
    public void deleteAccount(@NotNull @Positive Long accountId) {
        if (!accountRepository.existsById(accountId))
            throw new AccountNotFoundException(accountId);
        accountRepository.deleteById(accountId);
    }

    private Account findAccount(@NotNull @Positive Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    private Role findRole(@NotNull @Positive Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(roleId));
    }

    private Role findRole(@NotNull String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
    }

}
