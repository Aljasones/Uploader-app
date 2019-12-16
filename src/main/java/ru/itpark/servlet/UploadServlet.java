package ru.itpark.servlet;

import ru.itpark.service.FileService;

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

    @Override
    public void init() throws ServletException {
        try {
            fileService = new FileService();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServletException(e);
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
    }
}
