package org.example.backendstarter.FileAttachment.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.example.backendstarter.FileAttachment.dao.FileAttachmentDao;
import org.example.backendstarter.FileAttachment.dto.FileAttachmentDto;
import org.example.backendstarter.FileAttachment.entity.FileAttachment;
import org.example.backendstarter.FileAttachment.enums.FileDirectory;
import org.example.backendstarter.FileAttachment.mapper.FileAttachmentMapper;
import org.example.backendstarter.FileAttachment.services.AmazonS3Service;
import org.example.backendstarter.FileAttachment.services.FileAttachmentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileAttachmentServiceImpl implements FileAttachmentService {

    private final AmazonS3Service amazonS3Service;
    private final FileAttachmentDao fileAttachmentDao;
    private final FileAttachmentMapper fileAttachmentMapper;

    @Override
    @SneakyThrows
    public FileAttachmentDto uploadFile(MultipartFile multipartFile, FileDirectory fileDirectory) {

        String fileName = multipartFile.getOriginalFilename().trim().replace(" ", "-");
        String fileKey = fileDirectory.getDirectory() + System.currentTimeMillis() + '-' + fileName;

        File tempFile = File.createTempFile("temp-" + System.currentTimeMillis(), '.' + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(tempFile.toPath());

        amazonS3Service.uploadFile(fileKey, tempFile);

        FileAttachment fileAttachment = FileAttachment.builder()
                .name(fileName)
                .size(multipartFile.getSize())
                .contentType(multipartFile.getContentType())
                .key(fileKey)
                .path(fileKey)
                .build();
        Files.delete(tempFile.toPath());

        return fileAttachmentMapper.toDto(fileAttachmentDao.save(fileAttachment));

    }

    @Override
    public List<FileAttachmentDto> uploadMultipleFiles(List<MultipartFile> multipartFiles, FileDirectory fileDirectory) {
        List<FileAttachmentDto> results = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            FileAttachmentDto dto = this.uploadFile(multipartFile, fileDirectory);
            results.add(dto);
        }
        return results;
    }


    @Override
    @SneakyThrows
    public FileAttachmentDto getFileAttachmentById(Long id) {

        FileAttachment fileAttachment = fileAttachmentDao.findById(id);
        if (fileAttachment == null) {
            throw new FileNotFoundException("File not found");
        }
        return fileAttachmentMapper.toDto(fileAttachment);
    }

    @Override
    @SneakyThrows
    public void deleteFile(Long id) {
        FileAttachment fileAttachment = fileAttachmentDao.findById(id);
        if (fileAttachment == null) {
            throw new FileNotFoundException("File not found");
        }
        amazonS3Service.deleteFile(fileAttachment.getKey());

    }

    @Override
    public List<FileAttachmentDto> findAll() {
        return fileAttachmentMapper.toDto(fileAttachmentDao.findAll());
    }

}
