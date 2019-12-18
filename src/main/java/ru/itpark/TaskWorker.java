package ru.itpark;

import ru.itpark.service.FileService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class TaskWorker implements Callable<Map<String, List<String>>> {
    private FileService fileService;
    private String phrase;

    public TaskWorker(FileService fileService, String phrase) {
        this.fileService = fileService;
        this.phrase = phrase;
    }

    @Override
    public Map<String, List<String>> call() throws Exception {
        System.out.println("Start working ..." + phrase);
        return fileService.searchByPhrase(phrase);
    }

}
