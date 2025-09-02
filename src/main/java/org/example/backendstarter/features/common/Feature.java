package org.example.backendstarter.features.common;

import lombok.Getter;

@Getter
public enum Feature {

    // -------------------- USER MANAGEMENT --------------------
    LIST_USERS("list all users", "let the user list all existing users"),
    CREATE_USER("create user", "Let the user create/add a new user"),
    UPDATE_USER("update user", "Allow updating an existing user's details"),
    FEAT_RESET_USER_PASSWORD("reset user password", "Allow reset user password"),
    DELETE_USER("delete user", "Allow deleting an existing user's details"),
    RESET_USER_PASSWORD("reset user password", "Allow resetting a user's password"),
    ASSIGN_ROLE_TO_USER("assign role to user", "Assign a role to a specific user"),
    REVOKE_ROLE_FROM_USER("revoke role from user", "Revoke a role from a specific user"),

    // -------------------- ROLE MANAGEMENT --------------------
    LIST_ROLES("list roles", "let the user list all existing roles"),
    CREATE_ROLE("create role", "Create/add a new role in the system"),
    UPDATE_ROLE("update role", "Update an existing role's details"),
    DELETE_ROLE("delete role", "Delete an existing role"),
    ASSIGN_FEATURE_TO_ROLE("assign feature to role", "Assign a feature/permission to a role"),
    REVOKE_FEATURE_FROM_ROLE("revoke feature from role", "Revoke a feature/permission from a role");


    private final String title;
    private final String description;

    Feature(String title, String description) {
        this.title = title;
        this.description = description;
    }

}
