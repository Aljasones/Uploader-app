package ru.itpark.servlet;

import ru.itpark.TaskWorker;
import ru.itpark.repository.TaskRepository;
import ru.itpark.service.FileService;
import ru.itpark.service.TaskService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchServlet extends HttpServlet {
    private FileService fileService;
    TaskService taskService;
    DataSource dataSource;
    ExecutorService executor = Executors.newFixedThreadPool(5);

    @Override
    public void init() throws ServletException {
        try {
            InitialContext context = new InitialContext();
            final DataSource dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/db");
            final TaskRepository taskRepository = new TaskRepository(dataSource);
            taskService = new TaskService(taskRepository);
            fileService = new FileService();
            } catch (NamingException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phrase = req.getParameter("phrase");

        taskService.createTask(phrase, req.getSession().getId());
        TaskWorker taskWorker = new TaskWorker(fileService, phrase);
        Future<Map<String, List<String>>> resultSearch = executor.submit(taskWorker);

        resp.sendRedirect("/");
    }
}
