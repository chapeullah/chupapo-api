package org.chapeullah.chupapoapi.user.service;

import lombok.RequiredArgsConstructor;
import org.chapeullah.chupapoapi.user.dto.UpdateUserRequest;
import org.chapeullah.chupapoapi.user.exception.RoleNotFoundException;
import org.chapeullah.chupapoapi.user.exception.UserAlreadyExistsException;
import org.chapeullah.chupapoapi.user.exception.UserNotFoundException;
import org.chapeullah.chupapoapi.user.model.Role;
import org.chapeullah.chupapoapi.user.model.User;
import org.chapeullah.chupapoapi.user.repository.RoleRepository;
import org.chapeullah.chupapoapi.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(String username, String password, String roleName) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username);
        }
        Role role = roleRepository.findById(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
        String passwordHash = passwordEncoder.encode(password);
        User user = new User(username, passwordHash, role);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        if (request.username() != null
                && !user.getUsername().equals(request.username())) {
            if (userRepository.existsByUsername(request.username())) {
                throw new UserAlreadyExistsException(request.username());
            }
            user.setUsername(request.username());
        }
        if (request.password() != null) {
            user.setPasswordHash(passwordEncoder.encode(request.password()));
        }
        if (request.roleName() != null
                && !user.getRole().getName().equals(request.roleName())) {
            Role role = roleRepository.findById(request.roleName())
                    .orElseThrow(() -> new RoleNotFoundException(request.roleName()));
            user.setRole(role);
        }
        if (request.enabled() != null) {
            user.setEnabled(request.enabled());
        }
        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userRepository.delete(user);
    }
}
