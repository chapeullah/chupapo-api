package org.chapeullah.chupapoapi.account.repository;

import org.chapeullah.chupapoapi.account.model.Account;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUsername(String username);

    @EntityGraph(attributePaths = {"role", "role.permissions"})
    Optional<Account> findByUsername(String username);

    @Override
    @EntityGraph(attributePaths = "role")
    @NonNull
    Page<Account> findAll(@NonNull Pageable pageable);

}
