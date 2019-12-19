package ru.itpark;

import ru.itpark.model.Status;
import ru.itpark.model.Task;
import ru.itpark.repository.TaskRepository;
import ru.itpark.service.FileService;
import ru.itpark.service.TaskService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


public class TaskWorker implements Runnable {
    private TaskService taskService;
    private TaskRepository taskRepository;
    private DataSource dataSource;
    private FileService fileService;

    public TaskWorker() {
        try {
            InitialContext context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/db");
            taskRepository = new TaskRepository(dataSource);
            taskService = new TaskService(taskRepository);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        try {
            fileService = new FileService();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        List<Task> tasksToWork = taskService.getListTaskWithUpdatingTasksStatus(Status.Waiting, Status.Running);
        for (Task task : tasksToWork) {
            try {
                Map<String, List<String>> result = fileService.searchByPhrase(task.getPhrase());
                PrintWriter writer = new PrintWriter(new File("" +
                        "C:\\Programming\\RFC-uploader\\output.txt"));
                for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue() + "\n");
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<Task> tasksToComplete = taskService.getListTaskWithUpdatingTasksStatus(Status.Running, Status.Completed);
        System.out.println(tasksToComplete.toString() + "complete end");
    }
}
