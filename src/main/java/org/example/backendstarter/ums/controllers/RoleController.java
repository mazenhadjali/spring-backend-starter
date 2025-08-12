package org.example.backendstarter.ums.controllers;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.dto.payload.RoleFeatureRequest;
import org.example.backendstarter.ums.dto.payload.RoleRequest;
import org.example.backendstarter.ums.entity.Role;
import org.example.backendstarter.ums.services.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllUsers() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping
    public ResponseEntity<Role> createUser(@RequestBody RoleRequest request, UriComponentsBuilder uriBuilder) {
        Role created = roleService.createRole(request);
        URI location = uriBuilder
                .path("/api/v1/roles/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PostMapping("/{id}/features")
    public ResponseEntity<String> addFeatures(@PathVariable("id") Long id, @RequestBody RoleFeatureRequest request) {
        roleService.grantFeatureToRole(id, request.getFeature());
        return ResponseEntity.ok("Feature Granted to Role successfully");
    }

    @DeleteMapping("{id}/features")
    public ResponseEntity<String> removeFeatures(@PathVariable("id") Long id, @RequestBody RoleFeatureRequest request) {
        roleService.revokeFeatureFromRole(id, request.getFeature());
        return ResponseEntity.ok("Feature revoked");
    }


    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.updateRole(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
