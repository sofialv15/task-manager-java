package repository;

import config.DatabaseConnection;

import enums.Category;
import enums.Priority;
import model.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MySqlTaskRepository implements SqlTaskRepository {
    @Override
    public void save(Task task) {
        String sql = """
        INSERT INTO tasks
        (title, description, completed, creation_date, priority, category, due_date)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setBoolean(3, task.isCompleted());
            statement.setDate(4, Date.valueOf(task.getCreationDate()));
            statement.setString(5, task.getPriority().name());
            statement.setString(6, task.getCategory().name());

            if (task.getDueDate() == null) {
                statement.setNull(7, Types.DATE);
            } else {
                statement.setDate(7, Date.valueOf(task.getDueDate()));
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar la tarea", e);
        }
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
            throw new RuntimeException("Error al obtener las tareas", e);
        }

        return tasks;
    }


    @Override
    public Task findById(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                return new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("completed"),
                        rs.getDate("creation_date").toLocalDate(),
                        Priority.valueOf(rs.getString("priority")),
                        Category.valueOf(rs.getString("category")),
                        rs.getDate("due_date") == null
                                ? null
                                : rs.getDate("due_date").toLocalDate()
                );

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar la tarea", e);
        }

        return null;
    }

    @Override
    public void update(Task task) {
        String sql = """
        UPDATE tasks
        SET title = ?,
            description = ?,
            completed = ?,
            priority = ?,
            category = ?,
            due_date = ?
        WHERE id = ?
        """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setBoolean(3, task.isCompleted());
            statement.setString(4, task.getPriority().name());
            statement.setString(5, task.getCategory().name());

            if (task.getDueDate() == null) {
                statement.setNull(6, Types.DATE);
            } else {
                statement.setDate(6, Date.valueOf(task.getDueDate()));
            }

            statement.setInt(7, task.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la tarea", e);
        }

    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar la tarea", e);
        }
    }

    private Task mapResultSetToTask(ResultSet rs) throws SQLException {

        return new Task(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getBoolean("completed"),
                rs.getDate("creation_date").toLocalDate(),
                Priority.valueOf(rs.getString("priority")),
                Category.valueOf(rs.getString("category")),
                rs.getDate("due_date") == null
                        ? null
                        : rs.getDate("due_date").toLocalDate()
        );

    }
}
