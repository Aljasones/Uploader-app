package ru.itpark.service;

import ru.itpark.model.Status;
import ru.itpark.model.Task;
import ru.itpark.repository.TaskRepository;

public class TaskService {
    TaskRepository taskRepository;

   public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask (String phrase, String sessionId) {
        Task task = new Task();
        task.setPhrase(phrase);
        task.setSessionId(sessionId);
        task.setStatus(Status.Waiting);
        taskRepository.createTask(task);
    }
}
