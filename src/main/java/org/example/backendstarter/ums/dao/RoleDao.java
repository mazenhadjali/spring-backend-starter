package org.example.backendstarter.ums.dao;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.entity.Role;
import org.example.backendstarter.ums.repository.RoleRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleDao {

    private final RoleRepository roleRepository;

    @Cacheable(value = "roleExistsById", key = "#id", sync = true)
    public boolean existsById(Long id) {
        return roleRepository.existsById(id);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "findAUserByUsernameforAuth", allEntries = true),
                    @CacheEvict(value = "AUserById", allEntries = true),
                    @CacheEvict(value = "aUserByUsername", allEntries = true),
                    @CacheEvict(value = "allaUsers", allEntries = true),
                    @CacheEvict(value = "allRoles", allEntries = true),
                    @CacheEvict(value = "roleById", allEntries = true),
            }
    )
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "findAUserByUsernameforAuth", allEntries = true),
                    @CacheEvict(value = "AUserById", allEntries = true),
                    @CacheEvict(value = "aUserByUsername", allEntries = true),
                    @CacheEvict(value = "allaUsers", allEntries = true),
                    @CacheEvict(value = "allRoles", allEntries = true),
                    @CacheEvict(value = "roleById", allEntries = true),
            }
    )
    public void delete(Long id) {
        roleRepository.deleteById(id);
    }

}
