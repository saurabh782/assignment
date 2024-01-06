package com.example.demo.controller;

import com.example.demo.entry.FileEntry;
import com.example.demo.response.FileResponse;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController()
@RequestMapping("/files")
public class FileController {
    @Autowired
    FileService service;
    @PostMapping("/upload")
    FileResponse uploadFile(@RequestBody FileEntry entry) {
        FileResponse response = new FileResponse();
        try {
            FileEntry createdFile = service.uploadFile(entry);
            response.setData(Collections.singletonList(createdFile));
            return response;
        } catch (Exception e) {
            System.out.println("");
            return response;
        }
    }

    @GetMapping("/{fileReferenceId}")
    FileResponse retrieveFile(@PathVariable String fileReferenceId) {
        FileResponse response = new FileResponse();
        try {
            FileEntry file = service.getFile(fileReferenceId);
            response.setData(Collections.singletonList(file));
            return response;
        } catch (Exception e) {
            System.out.println("");
            return response;
        }
    }

    @GetMapping("/")
    FileResponse listFiles() {
        FileResponse response = new FileResponse();
        try {
            List<FileEntry> files = service.listFiles();
            response.setData(files);
            return response;
        } catch (Exception e) {
            System.out.println("");
            return response;
        }
    }

    @PutMapping("/{fileReferenceId}")
    FileResponse updateFile(@PathVariable String fileReferenceId, @RequestBody, FileEntry entry) {
        FileResponse response = new FileResponse();
        try {
            List<FileEntry> files = service.updateFile();
            response.setData(files);
            return response;
        } catch (Exception e) {
            System.out.println("");
            return response;
        }
    }
}
