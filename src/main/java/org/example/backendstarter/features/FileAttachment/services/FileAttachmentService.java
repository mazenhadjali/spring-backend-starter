package org.example.backendstarter.features.FileAttachment.services;

import org.example.backendstarter.features.FileAttachment.dto.FileAttachmentDto;
import org.example.backendstarter.features.FileAttachment.enums.FileDirectory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileAttachmentService {

    FileAttachmentDto uploadFile(MultipartFile multipartFile, FileDirectory fileDirectory);

    List<FileAttachmentDto> uploadMultipleFiles(List<MultipartFile> multipartFiles, FileDirectory fileDirectory);

    FileAttachmentDto getFileAttachmentById(Long id);

    void deleteFile(Long id);

    List<FileAttachmentDto> findAll();

}
