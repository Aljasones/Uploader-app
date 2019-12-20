package ru.itpark.servlet;

import ru.itpark.service.TaskService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SearchServlet extends HttpServlet {
    TaskService taskService;

    @Override
    public void init() {
        taskService = new TaskService();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String phrase = req.getParameter("phrase");
        taskService.createTask(phrase, req.getSession().getId());
        resp.sendRedirect("/");
    }
}