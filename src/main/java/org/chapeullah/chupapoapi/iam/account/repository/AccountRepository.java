package org.chapeullah.chupapoapi.iam.account.repository;

import org.chapeullah.chupapoapi.iam.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUsername(String username);

}
