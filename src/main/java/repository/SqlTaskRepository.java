package repository;


import model.Task;
import java.util.List;

public interface SqlTaskRepository {
    void save(Task task);

    List<Task> findAll();

    Task findById(int id);

    void update(Task task);

    void delete(int id);
}
