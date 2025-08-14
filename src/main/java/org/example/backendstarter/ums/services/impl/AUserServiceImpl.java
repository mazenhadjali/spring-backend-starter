package org.example.backendstarter.ums.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.dao.AUserDao;
import org.example.backendstarter.ums.dao.RoleDao;
import org.example.backendstarter.ums.dto.AUserDto;
import org.example.backendstarter.ums.dto.payload.CreateUserRequest;
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
    @Cacheable(value = "allUsers", sync = true)
    public List<AUserDto> getAllUsers() {
        return userMapper.toAuserDto(userDao.findAll());
    }

    @Override
    @Transactional
    @Caching(
            put = {
                    @CachePut(value = "usersById", key = "#result.id", unless = "#result == null"),
                    @CachePut(value = "usersByUsername", key = "#result.username", unless = "#result == null")
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
    @Cacheable(value = "usersById", key = "#id", sync = true)
    public AUserDto getUserById(Long id) {
        return userMapper.toAuserDto(userDao.findById(id));
    }

    @Override
    public AUserDto updateUser(Long id, UpdateUserRequest request) {
        AUser user = userDao.findById(id);
        userMapper.map(userMapper.toAuser(request), user);
        return userMapper.toAuserDto(userDao.save(user));
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
        user.getRoles().remove(role);
        userDao.save(user);
    }
}
