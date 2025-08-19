package org.example.backendstarter.FileAttachment.dao;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.FileAttachment.entity.FileAttachment;
import org.example.backendstarter.FileAttachment.repository.FileAttachmentRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FileAttachmentDaoImpl implements FileAttachmentDao {

    private final FileAttachmentRepository fileAttachmentRepository;

    @Override
    public FileAttachment save(FileAttachment fileAttachment) {
        return this.fileAttachmentRepository.save(fileAttachment);
    }

    @Override
    public List<FileAttachment> findAll() {
        return fileAttachmentRepository.findAll();
    }

    @Override
    public List<FileAttachment> findAllByIds(List<Long> ids) {
        return fileAttachmentRepository.findAllById(ids);
    }

    @Override
    public FileAttachment findById(Long id) {
        return fileAttachmentRepository.findById(id).orElse(null);
    }
}
