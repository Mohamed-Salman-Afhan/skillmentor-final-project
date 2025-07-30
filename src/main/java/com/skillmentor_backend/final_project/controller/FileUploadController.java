package com.skillmentor_backend.final_project.controller;

import com.skillmentor_backend.final_project.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        String url = fileUploadService.uploadFile(file);
        // Return the URL in a JSON object, e.g., { "url": "http://..." }
        return ResponseEntity.ok(Map.of("url", url));
    }
}