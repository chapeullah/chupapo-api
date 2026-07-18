package org.chapeullah.chupapoapi.iam.account.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.iam.access.application.RoleService;
import org.chapeullah.chupapoapi.iam.access.exception.RoleNotFoundException;
import org.chapeullah.chupapoapi.iam.access.model.Role;
import org.chapeullah.chupapoapi.iam.access.repository.RoleRepository;
import org.chapeullah.chupapoapi.iam.account.dto.*;
import org.chapeullah.chupapoapi.iam.account.exception.AccountAlreadyExistsException;
import org.chapeullah.chupapoapi.iam.account.exception.AccountNotFoundException;
import org.chapeullah.chupapoapi.iam.account.model.Account;
import org.chapeullah.chupapoapi.iam.account.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
                findRole(request.roleId()));
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccount(Long accountId) {
        return AccountResponse.from(findAccount(accountId));
    }

    @Transactional
    public AccountResponse updateAccountUsername(Long accountId, UpdateAccountUsernameRequest request) {
        Account account = findAccount(accountId);
        if (!account.getUsername().equals(request.username())
                && accountRepository.existsByUsername(request.username()))
            throw new AccountAlreadyExistsException(request.username());
        account.setUsername(request.username());
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional
    public AccountResponse updateAccountRole(Long accountId, UpdateAccountRoleRequest request) {
        Account account = findAccount(accountId);
        account.setRole(findRole(request.roleId()));
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional
    public AccountResponse updateAccountPassword(Long accountId, UpdateAccountPasswordRequest request) {
        Account account = findAccount(accountId);
        account.setPasswordHash(passwordEncoder.encode(request.password()));
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional
    public AccountResponse enableAccount(Long accountId) {
        Account account = findAccount(accountId);
        account.setEnabled(true);
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional
    public AccountResponse disableAccount(Long accountId) {
        Account account = findAccount(accountId);
        account.setEnabled(false);
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional
    public void deleteAccount(Long accountId) {
        if (accountRepository.existsById(accountId))
            throw new AccountAlreadyExistsException(accountId);
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

}
