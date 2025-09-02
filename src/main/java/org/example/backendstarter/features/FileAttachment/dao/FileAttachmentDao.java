package org.example.backendstarter.features.FileAttachment.dao;

import org.example.backendstarter.features.FileAttachment.entity.FileAttachment;

import java.util.List;


public interface FileAttachmentDao {
    FileAttachment save(FileAttachment fileAttachment);

    List<FileAttachment> findAll();

    List<FileAttachment> findAllByIds(List<Long> ids);

    FileAttachment findById(Long id);
}
