package ru.itpark;

import ru.itpark.model.Status;
import ru.itpark.model.Task;
import ru.itpark.service.FileService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class TaskWorker implements Runnable {
    private FileService fileService;
    private Task task;

    public TaskWorker(Task task) {
        try {
            fileService = new FileService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.task = task;
    }

//    @Override
//    public Map<String, List<String>> call() throws Exception {
//        System.out.println("Start working ..." + task.getPhrase());
//        return fileService.searchByPhrase(task.getPhrase());
//    }

//    public void writeResults() {
//        Map<String, List<String>> resultSearch = (Map<String, List<String>>) scheduler.submit(taskWorker);
//        try {
//            Map<String, List<String>> result = resultSearch.get();
//            try (PrintWriter writer = new PrintWriter(new File("" +
//                    "C:\\Programming\\RFC-uploader\\output.txt"))) {
//                for (Map.Entry<String, List<String>> entry : result.entrySet()) {
//                    writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

//        List<Task> list = taskRepository.getTasksByStatus(Status.Running);
//        System.out.println(list.toString() + "run");
//    }
//    taskService.getListTaskWithUpdatingTasksStatus(Status.Running,Status.Completed);
//    List<Task> list = taskRepository.getTasksByStatus(Status.Completed);
//        System.out.println(list.toString()+"end");

    @Override
    public void run() {
        System.out.println("Start working ..." + task.getPhrase());
        try {
            Map<String, List<String>> resultSearch = fileService.searchByPhrase(task.getPhrase());
            try (PrintWriter writer = new PrintWriter(new File("" +
                    "C:\\Programming\\RFC-uploader\\output.txt"))) {
                for (Map.Entry<String, List<String>> entry : resultSearch.entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
