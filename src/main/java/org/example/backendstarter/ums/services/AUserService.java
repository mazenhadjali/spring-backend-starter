package org.example.backendstarter.ums.services;

import org.example.backendstarter.ums.dto.AUserDto;
import org.example.backendstarter.ums.dto.payload.CreateUserRequest;
import org.example.backendstarter.ums.dto.payload.UpdateUserRequest;

import java.util.List;

public interface AUserService {

    List<AUserDto> getAllUsers();

    AUserDto createUser(CreateUserRequest request);

    AUserDto getUserById(Long id);

    AUserDto getUserByUsername(String username);

    AUserDto updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);

    void grantRole(Long userId, Long roleId);

    void revokeRole(Long userId, Long roleId);

}
