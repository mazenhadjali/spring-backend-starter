package org.example.backendstarter.ums.dto;

import lombok.Data;

import java.util.List;


@Data
public class AUserDtoWithPass {

    private Long id;
    private String username;
    private String email;

    private String firstName;
    private String lastName;
    private String phone;
    private String cin;
    private String password;

    private List<RoleDto> roles;
}
