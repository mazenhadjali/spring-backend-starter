package org.example.backendstarter.features.ums.repository;


import org.example.backendstarter.features.ums.entity.AUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AUserRepository extends JpaRepository<AUser, Long> {
    boolean existsByUsername(String username);
    Optional<AUser> findByUsername(String username);
}
