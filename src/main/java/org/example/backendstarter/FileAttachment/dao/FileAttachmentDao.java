package org.example.backendstarter.FileAttachment.dao;

import org.example.backendstarter.FileAttachment.entity.FileAttachment;

import java.util.List;


public interface FileAttachmentDao {
    FileAttachment save(FileAttachment fileAttachment);

    List<FileAttachment> findAll();

    List<FileAttachment> findAllByIds(List<Long> ids);

    FileAttachment findById(Long id);
}
