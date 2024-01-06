package com.example.demo.response;

import com.example.demo.entry.FileEntry;
import com.example.demo.entry.StatusEntry;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FileResponse {
    private StatusEntry status;
    private List<FileEntry> data;
}
