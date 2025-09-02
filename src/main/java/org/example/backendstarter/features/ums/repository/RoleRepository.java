package org.example.backendstarter.features.ums.repository;


import org.example.backendstarter.features.ums.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
