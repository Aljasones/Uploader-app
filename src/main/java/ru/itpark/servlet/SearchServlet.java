package ru.itpark.servlet;

import ru.itpark.repository.TaskRepository;
import ru.itpark.service.FileService;
import ru.itpark.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SearchServlet extends HttpServlet {
    private FileService fileService;
    TaskService taskService;

    @Override
    public void init() throws ServletException {
        try {
            taskService = new TaskService(new TaskRepository());
            fileService = new FileService();
                    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phrase = req.getParameter("phrase");

        taskService.createTask(phrase, req.getSession().getId());

        resp.sendRedirect("/");
    }
}
