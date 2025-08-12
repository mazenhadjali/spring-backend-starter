package org.example.backendstarter.ums.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.dao.AUserDao;
import org.example.backendstarter.ums.dto.AUserDto;
import org.example.backendstarter.ums.dto.payload.CreateUserRequest;
import org.example.backendstarter.ums.dto.payload.UpdateUserRequest;
import org.example.backendstarter.ums.entity.AUser;
import org.example.backendstarter.ums.mappers.AuserMapper;
import org.example.backendstarter.ums.services.AUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AUserServiceImpl implements AUserService {

    private final AUserDao userDao;
    private final AuserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AUserDto> getAllUsers() {
        return userMapper.toAuserDto(userDao.findAll());
    }

    @Override
    public AUserDto createUser(CreateUserRequest request) {
        if (userDao.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toAuserDto(userDao.save(userMapper.toAuser(request)));
    }

    @Override
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
}
