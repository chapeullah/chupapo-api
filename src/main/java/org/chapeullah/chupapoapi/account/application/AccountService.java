package org.chapeullah.chupapoapi.account.application;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        Account savedAccount = accountRepository.saveAndFlush(account);
        log.info("Account created: accountId={}, role={}",
                savedAccount.getId(),
                savedAccount.getRole().getName());
        return AccountResponse.from(savedAccount);
    }

    @Transactional(readOnly = true)
    public Page<AccountResponse> getAccounts(@NotNull Pageable pageable) {
        log.debug(
                "Getting accounts: page={}, size={}",
                pageable.getPageNumber(),
                pageable.getPageSize());
        return accountRepository.findAll(pageable).map(AccountResponse::from);
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccount(@NotNull @Positive Long accountId) {
        log.debug("Getting account: accountId={}", accountId);
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
        log.info("Account username updated: accountId={}", accountId);
        return AccountResponse.from(accountRepository.saveAndFlush(account));
    }

    @Transactional
    public AccountResponse updateAccountRole(
            @NotNull @Positive Long accountId,
            @Valid UpdateAccountRoleRequest request) {
        Account account = findAccount(accountId);
        account.setRole(findRole(request.roleId()));
        log.info("Account role updated: accountId={}", accountId);
        return AccountResponse.from(accountRepository.saveAndFlush(account));
    }

    @Transactional
    public AccountResponse updateAccountPassword(
            @NotNull @Positive Long accountId,
            @Valid UpdateAccountPasswordRequest request) {
        Account account = findAccount(accountId);
        account.setPasswordHash(passwordEncoder.encode(request.password()));
        log.info("Account password updated: accountId={}", accountId);
        return AccountResponse.from(accountRepository.saveAndFlush(account));
    }

    @Transactional
    public AccountResponse enableAccount(@NotNull @Positive Long accountId) {
        Account account = findAccount(accountId);
        account.setEnabled(true);
        log.info("Account enabled: accountId={}", accountId);
        return AccountResponse.from(accountRepository.saveAndFlush(account));
    }

    @Transactional
    public AccountResponse disableAccount(@NotNull @Positive Long accountId) {
        Account account = findAccount(accountId);
        account.setEnabled(false);
        log.info("Account disabled: accountId={}", accountId);
        return AccountResponse.from(accountRepository.saveAndFlush(account));
    }

    @Transactional
    public void deleteAccount(@NotNull @Positive Long accountId) {
        if (!accountRepository.existsById(accountId))
            throw new AccountNotFoundException(accountId);
        log.info("Account deleted: accountId={}", accountId);
        accountRepository.deleteById(accountId);
    }

    private Account findAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    private Role findRole(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(roleId));
    }

    private Role findRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
    }

}
