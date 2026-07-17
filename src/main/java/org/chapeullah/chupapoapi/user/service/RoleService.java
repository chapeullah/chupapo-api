package org.chapeullah.chupapoapi.user.service;

import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.user.model.Role;
import org.chapeullah.chupapoapi.user.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

}
