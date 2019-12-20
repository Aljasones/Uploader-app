package ru.itpark.servlet;

import org.apache.commons.io.FileUtils;
import ru.itpark.enums.RfcPath;
import ru.itpark.model.Task;
import ru.itpark.service.RfcPatchService;
import ru.itpark.service.TaskService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
        taskService = new TaskService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("download");
        String clearButton = req.getParameter("clearButton");

        Path path = Paths.get(RfcPatchService.getInstance().getPath(RfcPath.TMP_PATH));

        if (clearButton != null) {
            FileUtils.cleanDirectory(path.toFile());
        }


        if (fileName != null) {
            resp.setContentType("text/plain");
            resp.setHeader("Content-disposition", "attachment; filename=" + fileName + ".txt");
            try (InputStream in = new FileInputStream(path.toString() + "\\" + fileName);
                 OutputStream out = resp.getOutputStream()) {

                int ARBITARY_SIZE = 1048;
                byte[] buffer = new byte[ARBITARY_SIZE];

                int numBytesRead;
                while ((numBytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, numBytesRead);
                }
            }
        }

        List<Task> tasks = taskService.getStatusForWork();
        req.setAttribute("tasks", tasks);

        if (Files.exists(path)) {
            List<File> resultFiles = new ArrayList<>();
            Files.walk(path).filter(Files::isRegularFile).forEach(file -> {
                resultFiles.add(file.toFile());
            });
            req.setAttribute("itemsMap", resultFiles);
        }
        req.getRequestDispatcher("/WEB-INF/result.jsp").forward(req, resp);
    }
}
