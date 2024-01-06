package com.example.demo.entry;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileEntry {
    private String fileReferenceId;
    private String fileName;
    private String fileType;
    private Long sizeInBytes;
    private byte[] fileInBytes;
    private Long createdAt;
    private String filePath;
}
