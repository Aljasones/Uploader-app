package ru.itpark.servlet;

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


public class SearchServlet extends HttpServlet {
    private FileService fileService;
    TaskService taskService;
    DataSource dataSource;
    TaskRepository taskRepository;

    @Override
    public void init() throws ServletException {
        try {
            InitialContext context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:/comp/env/jdbc/db");
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
        taskService.startTasksToWork();
        resp.sendRedirect("/");
    }
}
