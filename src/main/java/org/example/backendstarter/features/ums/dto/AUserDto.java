package org.example.backendstarter.features.ums.dto;

import lombok.Data;
import org.example.backendstarter.features.FileAttachment.dto.FileAttachmentDto;

import java.util.List;


@Data
public class AUserDto {

    private Long id;
    private String username;
    private String email;

    private String firstName;
    private String lastName;
    private String phone;
    private String cin;

    private FileAttachmentDto avatar;

    private List<RoleDto> roles;
}
