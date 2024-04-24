package main.java.service;

import main.java.ManagerSaveException;
import main.java.intefaces.HistoryManager;
import main.java.tasks.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private static File file;
    private static File path;

//убрал отсюда мапы, historyManager и айди


    public static File getFile() {
        return file;
    }

    public FileBackedTasksManager(File file) {
        FileBackedTasksManager.file = file;
    }

// удалил не нужный конструктор




    public String toString(Task task) {
        return task.getTitle() + ":" + task.getDescription();
    }
// перенес TaskType в отдельный файл
    public static Task fromString(String value) {
        String[] parts = value.split(":");
        TaskType type = TaskType.valueOf(parts[0]);
        String description = parts[1];
        return new Task(type, description);
    }
// исправил
    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (br.ready()) {
                    Task task = null;
                    task = task;
                    final Task task2 = task;
                    final Task task3 = task;
                    if (task.getTitle().equals(TaskType.TASK)) {
                        fileBackedTasksManager.addTask(task);
                    } else if (task3.getTitle().equals(TaskType.EPIC)) {
                        fileBackedTasksManager.addEpic((Epic) task3);
                    } else if (task2.getTitle().equals(TaskType.SUBTASK)) {
                        fileBackedTasksManager.addSubTask((Subtask) task);

                    }

                    fileBackedTasksManager.getTaskById(task.getId());

                }

            }
        } catch (IOException exception) {
            throw new ManagerSaveException(exception.getMessage());
        }
        return fileBackedTasksManager;
    }

   //перенес класс с ManagerSaveException в отдельный файл

    // изменил логику
    private void save() {
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Task task : tasks.values()) {
                writer.println(task.toCSV());
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Error saving data", e);
        }
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Subtask subtask : subTasks.values()) {
                writer.println(subtask.toCSV());
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Error saving data", e);
        }
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Epic epic : epics.values()) {
                writer.println(epic.toCSV());
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Error saving data", e);
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(Subtask subTask) {
        super.addSubTask(subTask);
        save();
    }

    //убрал не нужный метод public void completeTask(int taskId)

    //убрал не нужный метод public void updateTask(int taskId, String name, Status status, String description)

//изменил логику данного метода
    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

   //убрал не нужный метод assignTaskToEpic(int taskId, int epicId)


    // убрал updateStatusEpic(Epic epic)
//изменил логику данного метода
    @Override
    public void updateSubtask(Subtask subTask) {
        super.updateSubtask(subTask);
        save();
    }

    //изменил логику данного метода
    @Override
    public void deleteTaskById(int taskId) {
        super.deleteTaskById(taskId);
        save();
    }

    //изменил логику данного метода
    @Override
    public void deleteEpicById(int epicId) {
        super.deleteEpicById(epicId);
        save();
    }

    //изменил логику данного метода
    @Override
    public void deleteSubTaskById(int subTaskId) {
        super.deleteSubTaskById(subTaskId);
        save();
    }

    //изменил логику данного метода
    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    //изменил логику данного метода
    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    //изменил логику данного метода
    @Override
    public void deleteAllSubTask() {
        super.deleteAllSubTask();
        save();
    }


    //изменил логику данного метода
    @Override
    public Task getTaskById(int taskId) {
        super.getTaskById(taskId);
        save();
        return null;
    }

    //изменил логику данного метода
    @Override
    public Subtask getSubTaskById(int subTaskId) {
        super.getSubTaskById(subTaskId);
        save();
        return null;
    }

    //изменил логику данного метода
    @Override
    public Epic getEpicById(int epicId) {
        super.getEpicById(epicId);
        save();
        return null;
    }
    // убрал не нужные методы из этого класса
}