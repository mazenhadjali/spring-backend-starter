package org.example.backendstarter.features.FileAttachment.dto;

import lombok.Data;

@Data
public class FileAttachmentDto {

    private Long id;

    private String name;
    private String contentType;
    private Long size;
    private String key;
    private String path;

}
