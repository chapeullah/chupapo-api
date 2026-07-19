package org.chapeullah.chupapoapi.bootstrap;

import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.iam.authorization.application.RoleService;
import org.chapeullah.chupapoapi.iam.account.application.AccountService;
import org.chapeullah.chupapoapi.iam.account.dto.CreateAccountRequest;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
public class BootstrapAdminInitializer implements ApplicationRunner {

    private final RoleService roleService;
    private final AccountService accountService;

    @Override
    public void run(ApplicationArguments args) {
        accountService.createAccount(new CreateAccountRequest("ADMIN", "ADMIN1234567", "ADMIN"));
    }
}
