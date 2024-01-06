package com.example.demo.controller;

import com.example.demo.entry.FileEntry;
import com.example.demo.response.FileResponse;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
            response.setStatus("File uploaded successfully");
            return response;
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus("Error occurred while uploading file");
            return response;
        }
    }

    @GetMapping("/{fileReferenceId}")
    FileResponse retrieveFile(@PathVariable String fileReferenceId) {
        FileResponse response = new FileResponse();
        try {
            FileEntry file = service.getFile(fileReferenceId);
            if(Objects.isNull(file)) {
                response.setStatus("File not found");
            } else {
                response.setStatus("File retrieved successfully");
                response.setData(Collections.singletonList(file));
            }
            return response;
        } catch (Exception e) {
            response.setStatus("Error occurred while retrieving file");
            System.out.println(e);
            return response;
        }
    }

    @GetMapping("/")
    FileResponse listFiles() {
        FileResponse response = new FileResponse();
        try {
            List<FileEntry> files = service.listFiles();
            if(CollectionUtils.isEmpty(files)) {
                response.setStatus("No files found in the system");
            } else {
                response.setStatus("Files retrieved successfully");
                response.setData(files);
            }
            return response;
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus("Error occurred while retrieving files");
            return response;
        }
    }

    @PutMapping("/{fileReferenceId}")
    FileResponse updateFile(@PathVariable String fileReferenceId, @RequestBody FileEntry entry) {
        FileResponse response = new FileResponse();
        try {
            FileEntry updatedFile = service.updateFile(fileReferenceId, entry);
            if(Objects.isNull(updatedFile)) {
                response.setStatus("File not found");
            } else {
                response.setStatus("File updated successfully");
                response.setData(Collections.singletonList(updatedFile));
            }
            return response;
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus("Error occurred while updating file");
            return response;
        }
    }

    @DeleteMapping("/{fileReferenceId}")
    FileResponse deleteFile(@PathVariable String fileReferenceId) {
        FileResponse response = new FileResponse();
        try {
            FileEntry file = service.deleteFile(fileReferenceId);
            if(Objects.isNull(file)) {
                response.setStatus("File not found");
            } else {
                response.setStatus("File deleted successfully");
            }
            return response;
        } catch (Exception e) {
            System.out.println(e);
            response.setStatus("Error occured hile deleting file");
            return response;
        }
    }
}
