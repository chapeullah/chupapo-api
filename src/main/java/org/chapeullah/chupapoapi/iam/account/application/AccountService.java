package org.chapeullah.chupapoapi.iam.account.application;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.iam.access.application.RoleService;
import org.chapeullah.chupapoapi.iam.access.model.Role;
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
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountResponse createAccount(@Valid CreateAccountRequest request) {
        if (accountRepository.existsByUsername(request.username()))
            throw new AccountAlreadyExistsException(request.username());
        Role role = roleService.getRole(request.roleName());
        Account account =
                new Account(request.username(), passwordEncoder.encode(request.password()), role);
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Long id) {
        return AccountResponse.from(findAccount(id));
    }

    @Transactional
    public AccountResponse renameAccount(Long id, RenameAccountRequest request) {
        Account account = findAccount(id);
        if (!account.getUsername().equals(request.username())
                && accountRepository.existsByUsername(request.username()))
            throw new AccountAlreadyExistsException(request.username());
        account.rename(request.username());
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional
    public AccountResponse assignRole(Long id, AssignAccountRoleRequest request) {
        Account account = findAccount(id);
        account.assignRole(roleService.getRole(request.roleName()));
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional
    public AccountResponse enableAccount(Long id) {
        Account account = findAccount(id);
        account.enable();
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional
    public AccountResponse disableAccount(Long id) {
        Account account = findAccount(id);
        account.disable();
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional
    public AccountResponse resetPassword(Long id, ResetPasswordRequest request) {
        Account account = findAccount(id);
        account.changePasswordHash(passwordEncoder.encode(request.password()));
        return AccountResponse.from(accountRepository.save(account));
    }

    @Transactional
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    private Account findAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

}
