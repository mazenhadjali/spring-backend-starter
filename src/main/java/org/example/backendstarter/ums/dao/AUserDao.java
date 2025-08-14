package org.example.backendstarter.ums.dao;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.dto.AUserDtoWithPass;
import org.example.backendstarter.ums.entity.AUser;
import org.example.backendstarter.ums.mappers.AuserMapper;
import org.example.backendstarter.ums.repository.AUserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AUserDao {

    private final AUserRepository repository;
    private final AuserMapper auserMapper;

    public List<AUser> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "allUsers", allEntries = true),
                    @CacheEvict(value = "existsById", allEntries = true),
                    @CacheEvict(value = "existsByUsername", allEntries = true),
                    @CacheEvict(value = "usersByUsername", allEntries = true),
                    @CacheEvict(value = "loadUserByUsername", allEntries = true)
            }
    )
    public AUser save(AUser user) {
        return repository.save(user);
    }

    public AUser findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public AUser findByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    @Cacheable(value = "findByUsernameforAuth", key = "#username")
    public AUserDtoWithPass findByUsernameforAuth(String username) {
        return auserMapper.toAUserDtoWithPass(repository.findByUsername(username).orElse(null));
    }

    @Cacheable(value = "existsById", key = "#id")
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Cacheable(value = "existsByUsername", key = "#username")
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "usersById", key = "#id"),
            @CacheEvict(value = "usersByUsername", allEntries = true),
            @CacheEvict(value = "allUsers", allEntries = true),
            @CacheEvict(value = "existsById", allEntries = true),
            @CacheEvict(value = "existsByUsername", allEntries = true),
            @CacheEvict(value = "findByUsernameforAuth", allEntries = true)
    })
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
