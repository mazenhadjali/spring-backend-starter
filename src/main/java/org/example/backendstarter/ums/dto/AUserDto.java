package org.example.backendstarter.ums.dto;

import lombok.Data;
import org.example.backendstarter.ums.entity.Role;

import java.util.Date;
import java.util.List;


@Data
public class AUserDto {

    private Long id;
    private String username;
    private String email;

    private String firstName;
    private String lastName;
    private String phone;

    private Date lastLogin;

    private List<Role> roles;
}
