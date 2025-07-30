package com.skillmentor_backend.final_project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadService {

    // Define the path to the upload directory.
    // This will create an 'uploads' folder in your project's root directory.
    private final Path root = Paths.get("uploads");

    public FileUploadService() {
        try {
            // Create the directory if it doesn't exist
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!", e);
        }
    }

    public String uploadFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }

            // Generate a unique filename to prevent overwrites
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // Resolve the file path and save the file
            Path destinationFile = this.root.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), destinationFile);

            // Return the accessible URL of the file
            // Note: This requires configuring Spring to serve static files from this directory.
            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(uniqueFilename)
                    .toUriString();

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }
}