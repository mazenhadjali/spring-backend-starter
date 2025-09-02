package org.example.backendstarter.features.FileAttachment.repository;


import org.example.backendstarter.features.FileAttachment.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {
}
