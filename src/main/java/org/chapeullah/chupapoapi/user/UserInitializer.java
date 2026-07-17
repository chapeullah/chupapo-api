package org.chapeullah.chupapoapi.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.user.model.Role;
import org.chapeullah.chupapoapi.user.model.User;
import org.chapeullah.chupapoapi.user.repository.RoleRepository;
import org.chapeullah.chupapoapi.user.repository.UserRepository;
import org.chapeullah.chupapoapi.user.service.UserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(2)
public class UserInitializer implements ApplicationRunner {

    private final UserService userService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        userService.createUser("admin", "password-hash", "ADMIN");
        userService.createUser("PRIVET", "POKA123456", "ADMIN");
    }

}
