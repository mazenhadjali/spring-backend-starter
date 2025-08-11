package org.example.backendstarter.ums.dao;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.entity.Role;
import org.example.backendstarter.ums.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleDao {

    private final RoleRepository roleRepository;

    Role save(Role role) {
        return roleRepository.save(role);
    }

    Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

}
