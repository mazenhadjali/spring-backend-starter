package org.example.backendstarter.features.FileAttachment.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.backendstarter.features.FileAttachment.services.AmazonS3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.File;
import java.net.URL;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AmazonS3ServiceImpl implements AmazonS3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;


    @Value("${minio.bucket}")
    private String bucketName;

    @Value("${minio.file-signature-duration}")
    private Long fileSignatureDuration;

    @Override
    @SneakyThrows
    public void uploadFile(String objectKey, File file) {
        PutObjectRequest putObject = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .cacheControl("public, max-age=86400, immutable")
                .build();
        s3Client.putObject(putObject, RequestBody.fromFile(file));
    }

    @Override
    @SneakyThrows
    public void deleteFile(String objectKey) {
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(objectKey).build());
    }

    @Override
    @SneakyThrows
    public URL downloadFileUrl(String objectKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(fileSignatureDuration))
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url();
    }
}
