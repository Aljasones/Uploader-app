package ru.itpark.repository;

import ru.itpark.model.Status;
import ru.itpark.model.Task;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private DataSource dataSource;

    public TaskRepository() {
        InitialContext context = null;
        try {
            context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/db");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        try (Connection conn = dataSource.getConnection()) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS tasks (id TEXT PRIMARY KEY, phrase TEXT NOT NULL, status TEXT NOT NULL, sessionId TEXT NOT NULL)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTask(Task task) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO tasks (id, phrase, status, sessionId) VALUES (?,?,?,?)");

            preparedStatement.setString(1, task.getId());
            preparedStatement.setString(2, task.getPhrase());
            preparedStatement.setString(3, task.getStatus().toString());
            preparedStatement.setString(4, task.getSessionId());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateTask(Task task) {
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks set status=? where id=?");
            preparedStatement.setString(1, task.getStatus().toString());
            preparedStatement.setString(2, task.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTasksByStatus(Status status) {
        try (Connection connection = dataSource.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT *  FROM tasks WHERE status=? LIMIT 5");
            preparedStatement.setString(1, status.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                Task task = new Task();
                task.setSessionId(resultSet.getString("sessionid"));
                task.setStatus(Status.valueOf(resultSet.getString("status")));
                task.setPhrase(resultSet.getString("phrase"));
                task.setId(resultSet.getString("id"));
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Task> getUpdateTasksStatus(Status status, Status newStatus) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            List<Task> tasksByStatus = getTasksByStatus(status);

            for (Task task : tasksByStatus) {
                task.setStatus(newStatus);
                updateTask(task);
            }

            connection.commit();
            return tasksByStatus;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}