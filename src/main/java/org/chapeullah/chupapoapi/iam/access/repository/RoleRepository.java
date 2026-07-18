package org.chapeullah.chupapoapi.iam.access.repository;

import org.chapeullah.chupapoapi.iam.access.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);

}
