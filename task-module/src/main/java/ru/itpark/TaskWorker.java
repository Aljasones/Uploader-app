package ru.itpark;

import ru.itpark.model.Status;
import ru.itpark.model.Task;
import ru.itpark.service.FileService;
import ru.itpark.service.TaskService;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class TaskWorker extends Thread {
    private FileService fileService;
    private Task task;
    private TaskService taskService;



    public TaskWorker(Task task) {
        this.task = task;
        taskService = new TaskService();
        try {
            fileService = new FileService();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String phrase = task.getPhrase();
        try {
            writeResultFile(fileService.searchByPhrase(phrase), phrase);
            task.setStatus(Status.Completed);
            taskService.updateTask(task);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void writeResultFile (Map<String, List<String>> result, String phrase) {
        fileService.writeResultFile(result, phrase);
    }
}
