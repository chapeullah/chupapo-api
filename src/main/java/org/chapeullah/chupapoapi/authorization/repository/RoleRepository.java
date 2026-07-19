package org.chapeullah.chupapoapi.authorization.repository;

import org.chapeullah.chupapoapi.authorization.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);
    Optional<Role> findByName(String name);

}
