package org.example.backendstarter.FileAttachment.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.net.URI;

@Configuration
public class S3Config {

    @Value("${minio.endpoint}")
    private String minioEndpoint;

    @Value("${minio.access-key}")
    private String minioAccessKey;

    @Value("${minio.secret-key}")
    private String minioSecretKey;

    @Value("${minio.region}")
    private String minioRegion;
    @Value("${minio.enable-path-style}")
    private String minioEnablePathStyle;


    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create(minioEndpoint))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(minioAccessKey, minioSecretKey)
                ))
                .forcePathStyle(minioEnablePathStyle.equals("true"))
                .region(Region.of(minioRegion))
                .build();
    }

    @Bean(destroyMethod = "close")
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.of(minioRegion))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

}
