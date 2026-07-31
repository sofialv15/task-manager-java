package service;

import  model.Task;
import java.util.ArrayList;
import java.util.List;
import repository.MySqlTaskRepository;

public class TaskService {

    private List<Task> tasks;
    private final MySqlTaskRepository repository = new MySqlTaskRepository();

    public TaskService(){
        tasks = repository.findAll();
    }

    public void addTask(String title, String description) {
        Task task = new Task(title, description);

        repository.save(task);
        tasks = repository.findAll();
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public boolean completeTask(int id) {
        Task task = repository.findById(id);

        if (task == null) {
            return false;
        }

        task.markAsCompleted();

        repository.update(task);

        return true;
    }

    public boolean deleteTask(int id) {
        Task task = repository.findById(id);

        if (task == null) {
            return false;
        }

        repository.delete(id);

        return true;
    }

    public List<Task> searchTasks(String text) {
        List<Task> results = new ArrayList<>();

        for (Task task : repository.findAll()) {

            if (task.getTitle().toLowerCase().contains(text.toLowerCase())
                    || task.getDescription().toLowerCase().contains(text.toLowerCase())) {

                results.add(task);
            }
        }
        return results;
    }

    public boolean editTask(int id, String newTitle, String newDescription) {
        Task task = repository.findById(id);

        if (task == null) {
            return false;
        }

        task.setTitle(newTitle);
        task.setDescription(newDescription);
        repository.update(task);

        return true;
    }
}
