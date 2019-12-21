package ru.itpark.sheduler;

import ru.itpark.service.TaskService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class TaskScheduler implements ServletContextListener {

    private ScheduledExecutorService scheduler;
    private TaskService taskService;

    public TaskScheduler() {
        taskService = new TaskService();
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newScheduledThreadPool(4);
        scheduler.scheduleAtFixedRate(() -> taskService.startTasksToWork(),0,10, TimeUnit.SECONDS);
//        scheduler.scheduleAtFixedRate(() ->  taskService.startTasksToWork(), 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
}
