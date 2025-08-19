package org.example.backendstarter.FileAttachment.repository;


import org.example.backendstarter.FileAttachment.entity.FileAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {
}
