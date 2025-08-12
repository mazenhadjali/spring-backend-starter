package org.example.backendstarter.ums.services;

import org.example.backendstarter.ums.dto.payload.RoleRequest;
import org.example.backendstarter.ums.entity.Role;

import java.util.List;

public interface RoleService {

    Role createRole(RoleRequest request);

    List<Role> getAllRoles();

    Role updateRole(Long id, RoleRequest request);

    Role getRoleById(Long id);

    void deleteRole(Long id);

    void grantFeatureToRole(Long roleId, String feature);

    void revokeFeatureFromRole(Long roleId, String feature);

}
