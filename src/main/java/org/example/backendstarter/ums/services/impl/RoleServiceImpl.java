package org.example.backendstarter.ums.services.impl;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.common.Feature;
import org.example.backendstarter.ums.dao.RoleDao;
import org.example.backendstarter.ums.dto.RoleDto;
import org.example.backendstarter.ums.dto.payload.RoleRequest;
import org.example.backendstarter.ums.entity.Role;
import org.example.backendstarter.ums.mappers.RoleMapper;
import org.example.backendstarter.ums.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;
    private final ModelMapper modelMapper;
    private final RoleMapper roleMapper;


    @Override
    @Caching(
            put = {
                    @CachePut(value = "roleById", key = "#result.id", unless = "#result == null"),
            }
    )
    public RoleDto createRole(RoleRequest request) {
        Role role = modelMapper.map(request, Role.class);
        return roleMapper.toDto(roleDao.save(role));
    }

    @Override
    @Cacheable(value = "allRoles", sync = true)
    public List<RoleDto> getAllRoles() {
        return roleMapper.toDtos(roleDao.findAll());
    }

    @Override
    public Role updateRole(Long id, RoleRequest request) {
        Role existingRole = roleDao.findById(id);
        if (existingRole == null) {
            throw new IllegalArgumentException("Role not found");
        }
        modelMapper.map(request, existingRole);
        return roleDao.save(existingRole);
    }

    @Override
    @Cacheable(value = "roleById", key = "#id", sync = true)
    public RoleDto getRoleById(Long id) {
        return roleMapper.toDto(roleDao.findById(id));
    }

    @Override
    public void deleteRole(Long id) {
        roleDao.delete(id);
    }

    @Override
    public void grantFeatureToRole(Long roleId, String feature) {
        Role role = roleDao.findById(roleId);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        Feature featureEnum = Feature.valueOf(feature);
        role.getFeatures().add(featureEnum);
        roleDao.save(role);
    }

    @Override
    public void revokeFeatureFromRole(Long roleId, String feature) {
        Role role = roleDao.findById(roleId);
        if (role == null) {
            throw new IllegalArgumentException("Role not found");
        }
        Feature featureEnum = Feature.valueOf(feature);
        role.getFeatures().remove(featureEnum);
        roleDao.save(role);
    }
}
