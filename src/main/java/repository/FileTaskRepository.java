package repository;

import model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileTaskRepository implements TaskRepository{

    @Override
    public void saveTasks(List<Task> tasks){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {

            for (Task task : tasks) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar las tareas.");
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> loadTasks() {

        List<Task> tasks = new ArrayList<>();

        File file = new File("tasks.txt");

        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                Task task = new Task(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        Boolean.parseBoolean(data[3]),
                        LocalDate.parse(data[4])
                );
                tasks.add(task);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
