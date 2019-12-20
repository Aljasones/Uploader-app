package ru.itpark.servlet;

import ru.itpark.model.Task;
import ru.itpark.repository.TaskRepository;
import ru.itpark.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/result")
public class DownloadServlet extends HttpServlet {
    private TaskService taskService;
    @Override
    public void init() throws ServletException {
        taskService = new TaskService(new TaskRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        List<Task> tasks = taskService.getStatusForWork();
        req.setAttribute("tasks", tasks);


        Path path = Paths.get("C:\\Programming\\RFC-uploader\\files\\");
        if (Files.exists(path)) {
            List<File> resultFiles = new ArrayList<>();
            Files.walk(path).filter(Files::isRegularFile).forEach(file -> {
                resultFiles.add(file.toFile());
            });
            req.setAttribute("itemsMap", resultFiles);

            req.getRequestDispatcher("/WEB-INF/result.jsp").forward(req, resp);


        }
    }
}
