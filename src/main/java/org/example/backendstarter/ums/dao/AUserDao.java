package org.example.backendstarter.ums.dao;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.entity.AUser;
import org.example.backendstarter.ums.repository.AUserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AUserDao {

    private final AUserRepository repository;

    public AUser save(AUser user) {
        return repository.save(user);
    }

    public AUser findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public AUser findByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public boolean deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
