package org.chapeullah.chupapoapi.bootstrap;

import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.authorization.application.RoleService;
import org.chapeullah.chupapoapi.account.application.AccountService;
import org.chapeullah.chupapoapi.account.dto.CreateAccountRequest;
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
        accountService.createAccount(new CreateAccountRequest("ADMIN", "@DMIN1234567890", "ADMIN"));
        for (int i = 1; i < 50; ++i)
            accountService.createAccount(
                    new CreateAccountRequest(
                            "VIEWER" + String.valueOf(i),
                            "@DMIN1234567890",
                            "VIEWER"));
    }

}
