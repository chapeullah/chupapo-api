package org.chapeullah.chupapoapi.bootstrap;

import org.chapeullah.chupapoapi.iam.access.repository.RoleRepository;
import org.chapeullah.chupapoapi.iam.account.model.Account;
import org.chapeullah.chupapoapi.iam.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class BootstrapAdminInitializer implements ApplicationRunner {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String username;
    private final String password;

    public BootstrapAdminInitializer(
            AccountRepository accountRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.bootstrap-admin.username:admin}") String username,
            @Value("${app.bootstrap-admin.password:}") String password) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (password.isBlank() || accountRepository.existsByUsername(username)) {
            return;
        }
        if (password.length() < 12 || password.length() > 128) {
            throw new IllegalStateException(
                    "Bootstrap admin password must be between 12 and 128 characters");
        }
        if (!username.matches("^[a-zA-Z0-9_]{5,32}$")) {
            throw new IllegalStateException(
                    "Bootstrap admin username must be 5-32 letters, digits, or underscores");
        }

        var adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new IllegalStateException("ADMIN role was not initialized"));
        accountRepository.save(new Account(
                username,
                passwordEncoder.encode(password),
                adminRole));
    }
}
