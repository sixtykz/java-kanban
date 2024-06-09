package main.java.service;

import main.java.Exceptions.ManagerSaveException;
import main.java.tasks.*;

import java.io.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private static File file;

    public FileBackedTasksManager(File file) {
        FileBackedTasksManager.file = file;
    }

    public String toString(Task task) {
        return task.getId() + "," + task.getTitle() + "," + task.getStatus() + "," + task.getDescription();
    }

    public static Task fromString(String value) {
        String[] parts = value.split(",");
        String taskType = parts[1];

        int count = 0;

        if (TaskType.TASK.toString().equals(TaskType.TASK)) {
            int id = count++;
            String title = parts[2];
            Status taskStatus = Status.valueOf(parts[3]);
            String description = parts[4];

            return new Task(id, title, taskStatus, description);
        } else if (TaskType.EPIC.toString().equals(TaskType.EPIC)) {
            int id = count++;
            String title = parts[2];
            Status epicStatus = Status.valueOf(parts[3]);
            String description = parts[4];

            return new Epic(id, title, epicStatus, description);
        } else if (TaskType.SUBTASK.toString().equals(TaskType.SUBTASK)) {
            int id = count++;
            String title = parts[2];
            Status subTaskStatus = Status.valueOf(parts[3]);
            String description = parts[4];

            return new Subtask(description, title, subTaskStatus, id);
        } else {
            return null;
        }
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
    public int createTask(Task task) {
       return super.createTask(task);
    }

    @Override
    public int createEpic(Epic epic) {
       return super.createEpic(epic);
    }

    @Override
    public int createSubTask(Subtask subTask) {
       return super.createSubTask(subTask);
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
    public void getTaskById(int taskId) {
        super.getTaskById(taskId);
        save();
    }

    @Override
    public void getSubTaskById(int subTaskId) {
        super.getSubTaskById(subTaskId);
        save();
    }

    @Override
    public void getEpicById(int epicId) {
        super.getEpicById(epicId);
        save();
    }
}