package ru.itpark.repository;

import ru.itpark.model.Status;
import ru.itpark.model.Task;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private final DataSource dataSource;

    public TaskRepository(DataSource dataSource) {
        this.dataSource = dataSource;

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS tasks (id TEXT PRIMARY KEY, phrase TEXT NOT NULL, status TEXT NOT NULL, sessionId TEXT NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTask(Task task) {
        try {
            Connection connection = dataSource.getConnection();
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
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks set status=? where id=?");
            preparedStatement.setString(1, task.getStatus().toString());
            preparedStatement.setString(2, task.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> getTasksByStatus(Status status) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT *  FROM tasks WHERE status=? LIMIT 10");
            preparedStatement.setString(1, status.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getString("id"));
                task.setPhrase(resultSet.getString("phrase"));
                task.setStatus(Status.valueOf(resultSet.getString("status")));
                task.setSessionId(resultSet.getString("sessionId"));
                tasks.add(task);
            }
            return tasks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Task> getUpdateTasksStatus(Status status, Status newStatus) {
        try  {
            Connection connection = dataSource.getConnection();
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