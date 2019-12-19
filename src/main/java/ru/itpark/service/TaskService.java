package ru.itpark.service;

import ru.itpark.TaskWorker;
import ru.itpark.TasksJobManager;
import ru.itpark.model.Status;
import ru.itpark.model.Task;
import ru.itpark.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

public class TaskService {
    TaskRepository taskRepository;

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

        }

    }

    public void updateTask(Task task){
        taskRepository.updateTask(task);
    }

    public List<Task> getListTaskWithUpdatingTasksStatus(Status status, Status newStatus) {
        return taskRepository.getUpdateTasksStatus(status, newStatus);
    }
}
