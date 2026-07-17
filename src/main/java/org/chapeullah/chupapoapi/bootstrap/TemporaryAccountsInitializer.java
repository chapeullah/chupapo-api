package org.chapeullah.chupapoapi.bootstrap;

import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.iam.account.application.AccountService;
import org.chapeullah.chupapoapi.iam.account.dto.CreateAccountRequest;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
public class TemporaryAccountsInitializer implements ApplicationRunner {

    private final AccountService accountService;

    @Override
    public void run(ApplicationArguments args) {
        accountService.createAccount(
                new CreateAccountRequest("admin", "password-hash", "ADMIN"));
        accountService.createAccount(
                new CreateAccountRequest("PRIVET", "POKA123456", "ADMIN"));
    }
}
