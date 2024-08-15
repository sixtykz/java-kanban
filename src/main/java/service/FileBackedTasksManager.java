package main.java.service;

import main.java.exceptions.ManagerSaveException;
import main.java.tasks.*;

import java.io.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private static File file;

    public FileBackedTasksManager(File file) {
        FileBackedTasksManager.file = file;
    }


    public static Task fromString(String value) {
        String[] parts = value.split(",");
        String taskType = parts[1];


        if (TaskType.TASK.toString().equals(taskType)) {
            int id = Integer.parseInt(parts[0]);
            String title = parts[2];
            Status status = Status.valueOf(parts[3]);
            String description = parts[4];

            return new Task(id, title, description, status);
        } else if (TaskType.EPIC.toString().equals(taskType)) {
            int id = Integer.parseInt(parts[0]);
            String title = parts[2];
            Status epicStatus = Status.valueOf(parts[3]);
            String description = parts[4];

            return new Epic(id, title, description, epicStatus);
        } else if (TaskType.SUBTASK.toString().equals(taskType)) {
            int id = Integer.parseInt(parts[0]);
            String title = parts[2];
            Status subTaskStatus = Status.valueOf(parts[3]);
            String description = parts[4];

            return new Subtask(id, title, description, subTaskStatus);
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

                    if (TaskType.TASK.equals(task.taskType)) {
                        fileBackedTasksManager.tasks.put(task.getId(), task);
                    } else if (TaskType.EPIC.equals(task.taskType)) {
                        fileBackedTasksManager.epics.put(task.getId(), (Epic) task);
                    } else if (TaskType.SUBTASK.equals(task.taskType)) {
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

         final String FILE_PATH = "task.csv";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks.values()) {
                writer.write(task.toString());
                writer.newLine();
            }
            for (Epic epic : epics.values()) {
                writer.write(epic.toString());
                writer.newLine();
            }
            for (Subtask subtask : subTasks.values()) {
                writer.write(subtask.toString());
                writer.newLine();
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