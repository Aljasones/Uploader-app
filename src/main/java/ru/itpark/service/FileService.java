package ru.itpark.service;

import ru.itpark.repository.FileRepository;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileService {
    private final String uploadPath;
    private PrintWriter writer;
    private String pathForSavingResult = "C:\\Programming\\RFC-uploader\\output.txt";
    private FileRepository fileRepository = new FileRepository();


    public FileService() throws IOException {
        uploadPath = System.getenv("UPLOAD_PATH");
        Files.createDirectories(Paths.get(uploadPath));
    }

    public String writeFile(Part part) throws IOException {
        String name = part.getSubmittedFileName();
        part.write(Paths.get(uploadPath).resolve(name).toString());
        return name;
    }

    public Map<String, List<String>> searchByPhrase(String phrase) throws IOException {

        List<File> fileList = Files.walk(Paths.get(uploadPath))
                .filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());

        return fileRepository.parseAllFilesByPhrase(fileList, phrase);
    }

    public void writeResultFile (Map<String, List<String>> result) {
        try {
            writer = new PrintWriter(new File(pathForSavingResult));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
            writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
        }
    }

}
