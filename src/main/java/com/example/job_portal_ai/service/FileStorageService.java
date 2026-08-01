package com.example.job_portal_ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
public class FileStorageService {


    @Value("${file.upload-dir}")
    private String uploadDir;


    public String saveFile(MultipartFile file) {

        try {

            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }


            String fileName = System.currentTimeMillis()
                    + "_"
                    + file.getOriginalFilename();


            Path filePath = uploadPath.resolve(fileName);


            Files.copy(
                    file.getInputStream(),
                    filePath,
                    StandardCopyOption.REPLACE_EXISTING
            );


            return fileName;


        } catch (IOException e) {

            throw new RuntimeException(
                    "Could not store file",
                    e
            );
        }
        //What this does:
        //Reads uploads/ location.
        //Creates the folder if missing.
        //Generates a unique file name:
    }
}