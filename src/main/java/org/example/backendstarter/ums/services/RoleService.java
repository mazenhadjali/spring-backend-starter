package org.example.backendstarter.ums.services;

import org.example.backendstarter.ums.dto.RoleDto;
import org.example.backendstarter.ums.dto.payload.RoleRequest;
import org.example.backendstarter.ums.entity.Role;

import java.util.List;

public interface RoleService {

    RoleDto createRole(RoleRequest request);

    List<RoleDto> getAllRoles();

    Role updateRole(Long id, RoleRequest request);

    RoleDto getRoleById(Long id);

    void deleteRole(Long id);

    void grantFeatureToRole(Long roleId, String feature);

    void revokeFeatureFromRole(Long roleId, String feature);

}
