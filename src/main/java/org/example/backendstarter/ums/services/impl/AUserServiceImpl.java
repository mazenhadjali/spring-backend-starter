package org.example.backendstarter.ums.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.dao.AUserDao;
import org.example.backendstarter.ums.dao.RoleDao;
import org.example.backendstarter.ums.dto.AUserDto;
import org.example.backendstarter.ums.dto.payload.CreateUserRequest;
import org.example.backendstarter.ums.dto.payload.ResetAUserPasswordRequest;
import org.example.backendstarter.ums.dto.payload.UpdateUserRequest;
import org.example.backendstarter.ums.entity.AUser;
import org.example.backendstarter.ums.entity.Role;
import org.example.backendstarter.ums.mappers.AuserMapper;
import org.example.backendstarter.ums.services.AUserService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AUserServiceImpl implements AUserService {

    private final AUserDao userDao;
    private final RoleDao roleDao;
    private final AuserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Cacheable(value = "allaUsers", sync = true)
    public List<AUserDto> getAllUsers() {
        return userMapper.toAuserDto(userDao.findAll());
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(value = "AUserById", key = "#result.id", unless = "#result == null"),
                    @CachePut(value = "aUserByUsername", key = "#result.username", unless = "#result == null")
            }
    )
    public AUserDto createUser(CreateUserRequest request) {
        if (userDao.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toAuserDto(userDao.save(userMapper.toAuser(request)));
    }

    @Override
    @Cacheable(value = "AUserById", key = "#id", unless = "#result == null")
    public AUserDto getUserById(Long id) {
        if (!userDao.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        return userMapper.toAuserDto(userDao.findById(id));
    }

    @Override
    @Cacheable(value = "aUserByUsername", key = "#username", unless = "#result == null")
    public AUserDto getUserByUsername(String username) {
        return userMapper.toAuserDto(userDao.findByUsername(username));
    }

    @Override
    public AUserDto updateUser(Long id, UpdateUserRequest request) {
        AUser user = userDao.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        userMapper.map(userMapper.toAuser(request), user);
        return userMapper.toAuserDto(userDao.save(user));
    }

    @Override
    public void resetPassword(Long id, ResetAUserPasswordRequest request) {
        AUser user = userDao.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("password and confirmation password do not match!");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userDao.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userDao.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        userDao.deleteById(id);
    }

    @Override
    public void grantRole(Long userId, Long roleId) {
        AUser user = userDao.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        Role role = roleDao.findById(roleId);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        user.getRoles().add(role);
        userDao.save(user);
    }

    @Override
    public void revokeRole(Long userId, Long roleId) {
        AUser user = userDao.findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        Role role = roleDao.findById(roleId);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        user.getRoles().removeIf(role1 -> role1.getId().equals(role.getId()));
        userDao.save(user);
    }
}
