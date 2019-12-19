package ru.itpark.service;

import ru.itpark.TaskWorker;
import ru.itpark.model.Status;
import ru.itpark.model.Task;
import ru.itpark.repository.TaskRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TaskService {
    private TaskRepository taskRepository;
    ExecutorService executor = Executors.newFixedThreadPool(5);

   public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask (String phrase, String sessionId) {
        Task task = new Task();
        String id = UUID.randomUUID().toString();
        task.setId(id);
        task.setPhrase(phrase);
        task.setSessionId(sessionId);
        task.setStatus(Status.Waiting);
        taskRepository.createTask(task);
    }

    public void startTasksToWork(){
        List<Task> tasksToWork = getListTaskWithUpdatingTasksStatus(Status.Waiting, Status.Running);
        for (Task task : tasksToWork) {
            executor.submit(new TaskWorker(task));
//            task.setStatus(Status.Completed);
//            taskRepository.updateTask(task);
        }


    }


    public List<Task> getListTaskWithUpdatingTasksStatus(Status status, Status newStatus) {
        return taskRepository.getUpdateTasksStatus(status, newStatus);
    }
}
