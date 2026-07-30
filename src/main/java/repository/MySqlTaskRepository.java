/** package repository;

import config.DatabaseConnection;
import enums.Category;
import enums.Priority;
import model.Task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlTaskRepository implements TaskRepository {
    @Override
    public void save(Task task) {

    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();

        String sql = "SELECT * FROM tasks";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()
        ) {

            while (result.next()) {

                Task task = new Task(
                        result.getInt("id"),
                        result.getString("title"),
                        result.getString("description"),
                        result.getBoolean("completed"),
                        result.getDate("creation_date").toLocalDate(),
                        Priority.valueOf(result.getString("priority")),
                        Category.valueOf(result.getString("category")),
                        result.getDate("due_date") == null
                                ? null
                                : result.getDate("due_date").toLocalDate()
                );

                tasks.add(task);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tasks;
    }

    @Override
    public Task findById(int id) {
        return null;
    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void delete(int id) {

    }
} **/
