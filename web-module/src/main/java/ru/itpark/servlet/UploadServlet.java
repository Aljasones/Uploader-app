package ru.itpark.servlet;

import ru.itpark.service.FileService;
import ru.itpark.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@MultipartConfig
public class UploadServlet extends HttpServlet {
    private FileService fileService;
    TaskService taskService;

    @Override
    public void init() {
        try {
            taskService = new TaskService();
            fileService = new FileService();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Part part = req.getPart("file");
        fileService.writeFile(part);
        resp.sendRedirect("/");

    }
}
