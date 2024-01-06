package com.example.demo.response;

import com.example.demo.entry.FileEntry;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FileResponse {
    private String status;
    private List<FileEntry> data;
}
