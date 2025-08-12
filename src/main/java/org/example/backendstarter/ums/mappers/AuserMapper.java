package org.example.backendstarter.ums.mappers;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.dto.AUserDto;
import org.example.backendstarter.ums.dto.payload.CreateUserRequest;
import org.example.backendstarter.ums.dto.payload.UpdateUserRequest;
import org.example.backendstarter.ums.entity.AUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuserMapper {

    private final ModelMapper mm = new ModelMapper();
    private final ModelMapper modelMapper;

    public AUserDto toAuserDto(AUser aUser) {
        return mm.map(aUser, AUserDto.class);
    }

    public AUser toAuser(AUserDto aUserDto) {
        return mm.map(aUserDto, AUser.class);
    }

    public AUser toAuser(CreateUserRequest createUserRequest) {
        return mm.map(createUserRequest, AUser.class);
    }

    public AUser toAuser(UpdateUserRequest updateUserRequest) {
        return mm.map(updateUserRequest, AUser.class);
    }

    public List<AUserDto> toAuserDto(List<AUser> aUsers) {
        return aUsers.stream().map(this::toAuserDto).collect(Collectors.toList());
    }

    public List<AUser> toAusers(List<AUserDto> aUserDtos) {
        return aUserDtos.stream().map(this::toAuser).collect(Collectors.toList());
    }

    public void map(AUser source, AUser destination) {
        modelMapper.map(source, destination);
    }

}
