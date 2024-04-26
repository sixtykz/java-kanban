package main.java.service;

import main.java.ManagerSaveException;
import main.java.tasks.*;

import java.io.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private static File file;
    private static Status psriority;

    public FileBackedTasksManager(File file) {
        FileBackedTasksManager.file = file;
    }

    @Override
    public String toString(Task task) {
        return task.getTitle() + "," + task.getDescription();
    }

    public static Task fromString(String value) {
        String[] parts = value.split(",");
        String name = parts[0];
        String description = parts[1];
        int priority = Integer.parseInt(parts[2]);

        return new Task(name, description, psriority);
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int maxId = 0;

            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    Task task = fromString(line);

                    if (task.getTitle().equals(TaskType.TASK)) {
                        fileBackedTasksManager.tasks.put(task.getId(), task);
                    } else if (task.getTitle().equals(TaskType.EPIC)) {
                        fileBackedTasksManager.epics.put(task.getId(), (Epic) task);
                    } else if (task.getTitle().equals(TaskType.SUBTASK)) {
                        fileBackedTasksManager.subTasks.put(task.getId(), (Subtask) task);
                    }

                    if (task.getId() > maxId) {
                        maxId = task.getId();
                    }
                }
            }

        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
        return fileBackedTasksManager;
    }


    private void save() {
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Task task : tasks.values()) {
                writer.println(task.toString());
            }
            for (Epic epic : epics.values()) {
                writer.println(epic.toString());
            }
            for (Subtask subtask : subTasks.values()) {
                writer.println(subtask.toString());
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Error saving data", e);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }


    @Override
    public void updateSubtask(Subtask subTask) {
        super.updateSubtask(subTask);
        save();
    }

    @Override
    public void deleteTaskById(int taskId) {
        super.deleteTaskById(taskId);
        save();
    }

    @Override
    public void deleteEpicById(int epicId) {
        super.deleteEpicById(epicId);
        save();
    }

    @Override
    public void deleteSubTaskById(int subTaskId) {
        super.deleteSubTaskById(subTaskId);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubTask() {
        super.deleteAllSubTask();
        save();
    }

    @Override
    public Task getTaskById(int taskId) {
        super.getTaskById(taskId);
        save();
        return null;
    }

    @Override
    public Subtask getSubTaskById(int subTaskId) {
        super.getSubTaskById(subTaskId);
        save();
        return null;
    }

    @Override
    public Epic getEpicById(int epicId) {
        super.getEpicById(epicId);
        save();
        return null;
    }

}