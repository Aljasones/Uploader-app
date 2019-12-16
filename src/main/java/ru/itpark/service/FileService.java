package ru.itpark.service;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileService {
    private final String uploadPath;


    public FileService() throws IOException {
        uploadPath = System.getenv("UPLOAD_PATH");
        Files.createDirectories(Paths.get(uploadPath));
    }

    public String writeFile(Part part) throws IOException {
        String name = part.getSubmittedFileName();
        part.write(Paths.get(uploadPath).resolve(name).toString());
        return name;
    }
}
