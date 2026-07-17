package org.chapeullah.chupapoapi.user.repository;

import org.chapeullah.chupapoapi.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {}