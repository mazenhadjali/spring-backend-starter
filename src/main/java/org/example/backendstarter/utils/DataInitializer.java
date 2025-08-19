package org.example.backendstarter.utils;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.backendstarter.common.Feature;
import org.example.backendstarter.ums.entity.AUser;
import org.example.backendstarter.ums.entity.Role;
import org.example.backendstarter.ums.repository.AUserRepository;
import org.example.backendstarter.ums.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final AUserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        // Skip if user already exists
        if (userRepository.existsByUsername("superadmin")) {
            return;
        }

        // Create admin role
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        adminRole.setFeatures(Set.of(Feature.values()));
        roleRepository.save(adminRole);

        AUser user = new AUser();
        user.setUsername("superadmin");
        user.setEmail("superadmin@example.com");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setCin("12345678");
        user.setPhone("+21623372100");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRoles(List.of(adminRole));

        userRepository.save(user);

    }
}
