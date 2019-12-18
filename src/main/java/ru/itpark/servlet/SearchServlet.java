package ru.itpark.servlet;

import ru.itpark.TaskWorker;
import ru.itpark.model.Task;
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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchServlet extends HttpServlet {
    private FileService fileService;
    TaskService taskService;
    DataSource dataSource;
    ExecutorService executor = Executors.newFixedThreadPool(5);
    TaskRepository taskRepository;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext context = new InitialContext();
            final DataSource dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/db");
            taskRepository = new TaskRepository(dataSource);
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
        try {
            Map<String, List<String>> result = resultSearch.get();


            try (PrintWriter writer = new PrintWriter(new File("" +
                    "C:\\Programming\\RFC-uploader\\output.txt"))) {
                for (Map.Entry<String, List<String>> entry : result.entrySet()) {
                    writer.write( entry.getKey() + ": " + entry.getValue() + "\n");
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        List<Task> list = taskRepository.selectTask();
        System.out.println(list.toString());
        resp.sendRedirect("/");
    }
}
