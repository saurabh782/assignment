package com.example.demo.respository;

import com.example.demo.entry.FileEntry;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class FileRepository {
    private final String BASE_PATH = "/Users/jalotra/saurabh";
    private List<FileEntry> entries = new ArrayList<>();
    public FileEntry save(FileEntry entry) throws IOException {
        Path path = Paths.get(BASE_PATH, entry.getFileName());
        Files.write(path, entry.getFileInBytes());

        entry.setFileReferenceId(UUID.randomUUID().toString());
        entry.setCreatedAt(System.currentTimeMillis());
        String fileLocation = BASE_PATH + "/" + entry.getFileName();
        entry.setFilePath(fileLocation);
        Path filePath = Paths.get(fileLocation);
        entry.setSizeInBytes(Files.size(filePath));
        entry.setFileInBytes(null);
        entries.add(entry);
        return entry;
    }

    public FileEntry get(String fileReferenceId) throws IOException {
        Optional<FileEntry> entry = entries.stream().filter(fileEntry -> fileEntry.getFileReferenceId().equals(fileReferenceId)).findFirst();

        if(entry.isEmpty()) {
            return null;
        }

        FileEntry retrievedEntry = entry.get();

        retrievedEntry.setFileInBytes(getFileFromLocalStorage(retrievedEntry.getFilePath()));
        return retrievedEntry;
    }

    private byte[] getFileFromLocalStorage(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }

    public List<FileEntry> getAllFiles() throws IOException {
        if(CollectionUtils.isEmpty(entries)) {
            return null;
        }

        for(FileEntry entry : entries) {
            entry.setFileInBytes(getFileFromLocalStorage(entry.getFilePath()));
        }

        return entries;
    }

    public FileEntry updateFile(String fileReferenceId, FileEntry updatedEntry) {
        Optional<FileEntry> entry = entries.stream().filter(fileEntry -> fileEntry.getFileReferenceId().equals(fileReferenceId)).findFirst();

        if(entry.isEmpty()) {
            return null;
        }

        FileEntry retrievedEntry = entry.get();

        retrievedEntry = updatedEntry;
        return retrievedEntry;
    }

    public FileEntry deleteFile(String fileReferenceId) throws IOException {
        Optional<FileEntry> entry = entries.stream().filter(fileEntry -> fileEntry.getFileReferenceId().equals(fileReferenceId)).findFirst();

        if(entry.isEmpty()) {
            return null;
        }

        FileEntry retrievedEntry = entry.get();
        Path path = Paths.get(retrievedEntry.getFilePath());
        Files.deleteIfExists(path);
        entries.remove(retrievedEntry);
        return retrievedEntry;
    }
}
