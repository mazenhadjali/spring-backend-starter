package org.example.backendstarter.features.FileAttachment.services;

import java.io.File;
import java.net.URL;


public interface AmazonS3Service {

    void uploadFile(String objectKey, File file);

    void deleteFile(String objectKey);

    URL downloadFileUrl(String objectKey);
}
