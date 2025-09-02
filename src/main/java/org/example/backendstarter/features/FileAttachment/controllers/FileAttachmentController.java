package org.example.backendstarter.features.FileAttachment.controllers;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.backendstarter.features.FileAttachment.dto.FileAttachmentDto;
import org.example.backendstarter.features.FileAttachment.enums.FileDirectory;
import org.example.backendstarter.features.FileAttachment.services.FileAttachmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileAttachmentController {

    private final FileAttachmentService fileAttachmentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileAttachmentDto> uploadOne(@RequestPart("file") @NotNull MultipartFile file, @RequestParam("fileDirectory") @NotNull FileDirectory fileDirectory) {
        FileAttachmentDto fileAttachmentDto = fileAttachmentService.uploadFile(file, fileDirectory);
        return ResponseEntity.ok(fileAttachmentDto);
    }

    @PostMapping(path = "/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<FileAttachmentDto>> uploadMany(@RequestPart("files") @NotNull List<MultipartFile> files, @RequestParam("fileDirectory") @NotNull FileDirectory fileDirectory) {
        List<FileAttachmentDto> fileAttachmentDtos = fileAttachmentService.uploadMultipleFiles(files, fileDirectory);
        return ResponseEntity.ok(fileAttachmentDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileAttachmentDto> getById(@PathVariable("id") Long id) {
        FileAttachmentDto dto = fileAttachmentService.getFileAttachmentById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        fileAttachmentService.deleteFile(id);
    }

    @GetMapping
    public ResponseEntity<List<FileAttachmentDto>> findAll() {
        return ResponseEntity.ok(fileAttachmentService.findAll());
    }
}
