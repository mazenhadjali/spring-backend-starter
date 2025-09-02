package org.example.backendstarter.features.ums.dto.payload;

import lombok.Data;

@Data
public class ResetAUserPasswordRequest {

    private String password;
    private String confirmPassword;
}
