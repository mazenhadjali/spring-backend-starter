package org.example.backendstarter.features.FileAttachment.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FileDirectory {
    PUBLIC("public/"),
    DOCUMENTS("documents/");

    private final String directory;
}
