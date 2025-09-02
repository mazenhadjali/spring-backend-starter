package org.example.backendstarter.features.ums.mappers;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.features.ums.dto.RoleDto;
import org.example.backendstarter.features.ums.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final ModelMapper modelMapper;

    public RoleDto toDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    public List<RoleDto> toDtos(List<Role> roles) {
        return roles.stream().map(this::toDto).collect(Collectors.toList());
    }

}
