package com.example.demo.service;

import com.example.demo.entry.FileEntry;
import com.example.demo.respository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    FileRepository repository;
    public FileEntry uploadFile(FileEntry entry) throws IOException {
        return repository.save(entry);
    }

    public FileEntry getFile(String fileReferenceId) throws IOException {
        return repository.get(fileReferenceId);
    }

    public List<FileEntry> listFiles() throws IOException {
        return repository.getAllFiles();
    }

    public FileEntry updateFile(String fileReferenceId, FileEntry entry) {
        return repository.updateFile(fileReferenceId, entry);
    }

    public FileEntry deleteFile(String fileReferenceId) throws IOException {
        return repository.deleteFile(fileReferenceId);
    }
}
