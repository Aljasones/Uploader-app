package ru.itpark;

import ru.itpark.model.Task;
import ru.itpark.service.FileService;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class TaskWorker implements Runnable {
    private FileService fileService;
    private Task task;


    public TaskWorker(Task task) {
        this.task = task;
        try {
            fileService = new FileService();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            writeResultFile(fileService.searchByPhrase(task.getPhrase()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void writeResultFile (Map<String, List<String>> result) {
        fileService.writeResultFile(result);
    }
}
