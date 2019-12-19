package ru.itpark.servlet;

import ru.itpark.repository.TaskRepository;
import ru.itpark.service.FileService;
import ru.itpark.service.TaskService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;
import java.io.IOException;

@MultipartConfig
public class UploadServlet extends HttpServlet {
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
        taskService.startTasksToWork();
        req.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Part part = req.getPart("file");
        fileService.writeFile(part);
        resp.sendRedirect("/");
        
    }
}
