package ui;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
import model.Task;
import service.TaskService;

public class Menu {

    private  Scanner scanner;
    private TaskService taskService;

    public Menu() {
        scanner = new Scanner(System.in);
        taskService = new TaskService();
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n==============================");
            System.out.println("       TASK MANAGER");
            System.out.println("==============================");
            System.out.println("1. Ver tareas");
            System.out.println("2. Agregar tarea");
            System.out.println("3. Completar tarea");
            System.out.println("4. Eliminar tarea");
            System.out.println("5. Buscar tarea");
            System.out.println("6. Editar tarea");
            System.out.println("7. Salir");

            System.out.print("Seleccione una opción: ");

            int option;

            try {
                option = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("\n❌ Debe ingresar un número.");

                scanner.nextLine();

                continue;
            }


            switch (option) {

                case 1: {
                    showTasks();
                    break;
                }

                case 2: {
                    addTask();
                    break;
                }

                case 3: {
                    completeTask();
                    break;

                }

                case 4: {
                    deleteTask();
                    break;
                }

                case 5: {
                    searchTask();
                    break;
                }

                case 6: {
                    ediTask();
                    break;
                }

                case 7: {
                    running = false;
                    System.out.println("¡Hasta luego!");
                    break;
                }

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }

        }

    }

    private void showTasks() {
        List<Task> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            System.out.println("\nNo hay tareas registradas.");
            return;
        }

        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private void addTask() {
        System.out.print("Ingrese el título: ");
        String title = scanner.nextLine();

        while (title.isBlank()) {
            System.out.println("❌ El título no puede estar vacío.");
            System.out.print("Ingrese el título: ");
            title = scanner.nextLine();
        }

        System.out.print("Ingrese la descripción: ");
        String description = scanner.nextLine();

        taskService.addTask(title, description);

        System.out.println("\n✅ Tarea agregada correctamente.");

    }

    private  void completeTask() {
        System.out.println("Ingrese el ID de la tarea: ");
        int id = Integer.parseInt(scanner.nextLine());

        boolean completed = taskService.completeTask(id);

        if (completed) {
            System.out.println("\n✅ Tarea completada correctamente.");
        } else {
            System.out.println("\n❌ No existe una tarea con ese ID.");
        }

    }

    private void deleteTask() {
        System.out.print("Ingrese el ID de la tarea: ");
        int idToDelete = scanner.nextInt();

        System.out.print("¿Está seguro? (S/N): ");
        String answer = scanner.nextLine();

        if (!answer.equalsIgnoreCase("S")) {
            System.out.println("Operación cancelada.");
            return;
        }

        if (taskService.deleteTask(idToDelete)) {
            System.out.println("\n🗑️ Tarea eliminada correctamente.");
        } else {
            System.out.println("\n❌ No existe una tarea con ese ID.");
        }
    }

    private void searchTask(){
        System.out.print("Ingrese el texto a buscar: ");
        String text = scanner.nextLine();

        List<Task> results = taskService.searchTasks(text);

        if (results.isEmpty()) {
            System.out.println("No se encontraron tareas.");
        } else {
            for (Task task : results) {
                System.out.println(task);
            }
        }
    }

    private void ediTask() {
        System.out.print("Ingrese el ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Nuevo título: ");
        String title = scanner.nextLine();

        while (title.isBlank()) {
            System.out.println("❌ El título no puede estar vacío.");
            System.out.print("Nuevo título: ");
            title = scanner.nextLine();
        }

        System.out.print("Nueva descripción: ");
        String description = scanner.nextLine();

        boolean updated = taskService.editTask(id, title, description);

        if (updated) {
            System.out.println("\n✅️ Tarea actualizada.");
        } else {
            System.out.println("\n❌ No existe una tarea con ese ID.");
        }
    }
}
