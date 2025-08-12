package org.example.backendstarter.ums.dao;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.entity.Role;
import org.example.backendstarter.ums.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleDao {

    private final RoleRepository roleRepository;

    public boolean existsById(Long id) {
        return roleRepository.existsById(id);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

}
