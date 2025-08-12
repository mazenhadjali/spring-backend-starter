package org.example.backendstarter.ums.dto.payload;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String username;
    private String email;
    private String password;

    private String firstName;
    private String lastName;
    private String phone;
    private String cin;
}
