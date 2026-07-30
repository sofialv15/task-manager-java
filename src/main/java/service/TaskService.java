package service;

import  model.Task;
import java.util.ArrayList;
import java.util.List;
import repository.FileTaskRepository;

public class TaskService {

    private List<Task> tasks;
    private  int nextId;
    private final FileTaskRepository fileManager = new FileTaskRepository();

    public TaskService(){
        tasks = new ArrayList<>();
        tasks = fileManager.loadTasks();
        nextId = tasks.size() + 1;

    }

    public void addTask(String title, String description) {
        Task task = new Task(nextId, title, description);

        tasks.add(task);
        fileManager.saveTasks(tasks);
        nextId++;
    }

    public List<Task> getAllTasks() {
        return tasks;
    }

    public boolean completeTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.markAsCompleted();
                fileManager.saveTasks(tasks);

                return true;
            }
        }
        return false;
    }

    public boolean deleteTask(int id) {
        boolean deleted = tasks.removeIf(task -> task.getId() == id);

        if (deleted) {
            fileManager.saveTasks(tasks);
        }

        return deleted;
    }

    public List<Task> searchTasks(String text) {
        List<Task> results = new ArrayList<>();
        String searchText = text.toLowerCase();
        for (Task task : tasks) {
            if (task.getTitle().toLowerCase().contains(searchText) || task.getDescription().toLowerCase().contains(searchText)) {
                results.add(task);
            }
        }
        return results;
    }

    public boolean editTask(int id, String newTitle, String newDescription) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setTitle(newTitle);
                task.setDescription(newDescription);
                fileManager.saveTasks(tasks);

                return true;
            }
        }
        return false;
    }
}
