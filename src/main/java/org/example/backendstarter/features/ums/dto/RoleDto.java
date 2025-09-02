package org.example.backendstarter.features.ums.dto;


import lombok.Data;
import org.example.backendstarter.features.common.Feature;

import java.util.Set;

@Data
public class RoleDto {

    private Long id;
    private String name;
    private String description;

    private Set<Feature> features;

}
