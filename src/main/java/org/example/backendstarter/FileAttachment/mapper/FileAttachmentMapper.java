package org.example.backendstarter.FileAttachment.mapper;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.FileAttachment.dto.FileAttachmentDto;
import org.example.backendstarter.FileAttachment.entity.FileAttachment;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FileAttachmentMapper {

    private final ModelMapper modelMapper;

    public FileAttachmentDto toDto(FileAttachment fileAttachment) {
        return modelMapper.map(fileAttachment, FileAttachmentDto.class);
    }

    public List<FileAttachmentDto> toDto(List<FileAttachment> fileAttachments) {
        return fileAttachments.stream().map(this::toDto).collect(Collectors.toList());
    }
}
