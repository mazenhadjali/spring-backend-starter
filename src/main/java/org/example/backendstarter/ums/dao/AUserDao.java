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
                    @CacheEvict(value = "allaUsers", allEntries = true),
                    @CacheEvict(value = "aUserExistsById", allEntries = true),
                    @CacheEvict(value = "aUserByUsername", allEntries = true),
                    @CacheEvict(value = "aUserxistsByUsername", allEntries = true),
                    @CacheEvict(value = "findAUserByUsernameforAuth", allEntries = true),
                    @CacheEvict(value = "AUserById", allEntries = true),


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

    @Cacheable(value = "findAUserByUsernameforAuth", key = "#username")
    public AUserDtoWithPass findByUsernameforAuth(String username) {
        return auserMapper.toAUserDtoWithPass(repository.findByUsername(username).orElse(null));
    }

    @Cacheable(value = "aUserExistsById", key = "#id")
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Cacheable(value = "aUserxistsByUsername", key = "#username")
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "AUserById", key = "#id"),
            @CacheEvict(value = "aUsersByUsername", allEntries = true),
            @CacheEvict(value = "allaUsers", allEntries = true),
            @CacheEvict(value = "aUserExistsById", allEntries = true),
            @CacheEvict(value = "aUserxistsByUsername", allEntries = true),
            @CacheEvict(value = "findAUserByUsernameforAuth", allEntries = true)
    })
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
