package ru.itpark.repository;

import ru.itpark.model.Task;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskRepository {
    private DataSource ds;
    private String url = "jdbc:sqlite:db.sqlite";

    public TaskRepository() {
//        InitialContext context = null;
//        try {
//            context = new InitialContext();
//            ds = (DataSource) context.lookup("java:/comp/env/db.sqlite");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//
//        Connection connection = null;
//        try {
//            connection = ds.getConnection();
//            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, phrase TEXT NOT NULL, status TEXT NOT NULL, sessionId TEXT NOT NULL);");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, phrase TEXT NOT NULL, status TEXT NOT NULL, sessionId TEXT NOT NULL);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void createTask(Task task) {
//        try (Connection connection = ds.getConnection()) {
        try (Connection connection = DriverManager.getConnection(url);) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO tasks (phrase, status, sessionId) VALUES (?,?,?)");

            preparedStatement.setString(1, task.getPhrase());
            preparedStatement.setString(2, task.getStatus().toString());
            preparedStatement.setString(3, task.getSessionId());
            int i = preparedStatement.executeUpdate();
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
