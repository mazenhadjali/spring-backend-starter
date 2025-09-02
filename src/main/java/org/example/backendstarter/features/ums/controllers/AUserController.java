package org.example.backendstarter.features.ums.controllers;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.features.ums.dto.AUserDto;
import org.example.backendstarter.features.ums.dto.payload.CreateUserRequest;
import org.example.backendstarter.features.ums.dto.payload.ResetAUserPasswordRequest;
import org.example.backendstarter.features.ums.dto.payload.UpdateUserRequest;
import org.example.backendstarter.features.ums.services.AUserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class AUserController {

    private final AUserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('FEAT_LIST_USERS')")
    public ResponseEntity<List<AUserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('FEAT_CREATE_USER')")
    public ResponseEntity<AUserDto> createUser(@RequestBody CreateUserRequest request, UriComponentsBuilder uriBuilder) {
        AUserDto created = userService.createUser(request);
        URI location = uriBuilder
                .path("/api/v1/users/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PreAuthorize("hasAuthority('FEAT_ASSIGN_ROLE_TO_USER')")
    @PostMapping("/{userId}/roles/{roleid}")
    public ResponseEntity<String> grantRole(@PathVariable Long userId, @PathVariable Long roleid) {
        userService.grantRole(userId, roleid);
        return ResponseEntity.ok("Role granted");
    }

    @PreAuthorize("hasAuthority('FEAT_REVOKE_ROLE_FROM_USER')")
    @DeleteMapping("/{userId}/roles/{roleid}")
    public ResponseEntity<String> revokeRole(@PathVariable Long userId, @PathVariable Long roleid) {
        userService.revokeRole(userId, roleid);
        return ResponseEntity.ok("Role revoked");
    }

    @PreAuthorize("hasAuthority('FEAT_LIST_USERS')")
    @GetMapping("/{id}")
    public ResponseEntity<AUserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasAuthority('FEAT_UPDATE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<AUserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @PreAuthorize("hasAuthority('FEAT_RESET_USER_PASSWORD')")
    @PutMapping("/{id}/resetpassword")
    public ResponseEntity<String> resetPassword(@PathVariable Long id, @RequestBody ResetAUserPasswordRequest request) {
        userService.resetPassword(id, request);
        return ResponseEntity.ok("Password reset");
    }

    @PreAuthorize("hasAuthority('FEAT_DELETE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
