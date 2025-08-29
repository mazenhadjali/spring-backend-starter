package org.example.backendstarter.ums.dto.payload;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String username;
    private String email;

    private String firstName;
    private String lastName;
    private String phone;
    private String cin;
}
