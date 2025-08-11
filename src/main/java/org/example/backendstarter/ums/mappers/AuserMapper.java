package org.example.backendstarter.ums.mappers;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.ums.dto.AUserDto;
import org.example.backendstarter.ums.entity.AUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuserMapper {

    private final ModelMapper mm = new ModelMapper();

    AUserDto toAuserDto(AUser aUser) {
        return mm.map(aUser, AUserDto.class);
    }

    AUser toAuser(AUserDto aUserDto) {
        return mm.map(aUserDto, AUser.class);
    }


}
