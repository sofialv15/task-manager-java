package model;

import java.time.LocalDate;
import enums.Priority;
import enums.Category;

public class Task {

    private int id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDate creationDate;

    private Priority priority;
    private Category category;
    private LocalDate dueDate;

    public Task(int id, String title, String description) {
        this(
                id,
                title,
                description,
                false,
                LocalDate.now(),
                Priority.MEDIUM,
                Category.OTHER,
                null
        );
    }

    public Task(int id,
                String title,
                String description,
                boolean completed,
                LocalDate creationDate) {

        this(
                id,
                title,
                description,
                completed,
                creationDate,
                Priority.MEDIUM,
                Category.OTHER,
                null
        );
    }

    public Task(int id,
                String title,
                String description,
                boolean completed,
                LocalDate creationDate,
                Priority priority,
                Category category,
                LocalDate dueDate) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.creationDate = creationDate;
        this.priority = priority;
        this.category = category;
        this.dueDate = dueDate;
    }

    public Task(String title, String description) {
        this(
                0,
                title,
                description,
                false,
                LocalDate.now(),
                Priority.MEDIUM,
                Category.OTHER,
                null
        );
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        String dueDateText = (dueDate == null)
                ? "Sin definir"
                : dueDate.toString();
        return """
            ------------------------------
            ID: %d
            Título: %s
            Descripción: %s
            Estado: %s
            Fecha: %s
            Prioridad: %s
            Categoría: %s
            Fecha límite: %s
            
            ------------------------------
            """.formatted(
                id,
                title,
                description,
                completed ? "✅ Completada" : "⏳ Pendiente",
                creationDate,
                priority,
                category,
                dueDateText

        );
    }

    public void markAsCompleted() {
        this.completed = true;
    }

    public String toFileFormat() {
        return id + ";" +
                title + ";" +
                description + ";" +
                completed + ";" +
                creationDate + "; + " +
                priority + ";" +
                category + ";" +
                dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}


