package ru.itpark;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.*;

@WebListener
public class TasksJobManager implements ServletContextListener {
    private ScheduledExecutorService scheduler;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newScheduledThreadPool(2);
        scheduler.scheduleAtFixedRate(new TaskWorker(), 0, 10, TimeUnit.SECONDS);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        scheduler.shutdownNow();
    }
}
