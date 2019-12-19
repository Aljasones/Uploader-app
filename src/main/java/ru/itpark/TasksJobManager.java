package ru.itpark;


import ru.itpark.model.Status;
import ru.itpark.model.Task;
import ru.itpark.repository.TaskRepository;
import ru.itpark.service.TaskService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.*;

@WebListener
public class TasksJobManager implements ServletContextListener {
    private ScheduledExecutorService scheduler;
    private TaskService taskService;
    private TaskRepository taskRepository;
    private DataSource dataSource;
    private TaskWorker taskWorker;

    public TasksJobManager() {
        try {
            InitialContext context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/db");
            taskRepository = new TaskRepository(dataSource);
            taskService = new TaskService(taskRepository);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = (ScheduledExecutorService) Executors.newFixedThreadPool(10);
        List<Task> tasksToWork = taskService.getListTaskWithUpdatingTasksStatus(Status.Waiting, Status.Running);
        for (Task task : tasksToWork) {
            scheduler.scheduleAtFixedRate(taskWorker = new TaskWorker(task), 0, 10, TimeUnit.SECONDS);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }
}
