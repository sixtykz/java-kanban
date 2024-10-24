package main.java.service;

import main.java.exceptions.ManagerSaveException;
import main.java.tasks.*;

import java.io.*;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private static File file;

    public FileBackedTasksManager(File file) {
        FileBackedTasksManager.file = file;
    }

    public String toString (Task task){
        String[] toJoin = {Integer.toString(task.getId()), TaskType.TASK.toString(), task.getTitle(),
                task.getStatus().toString(), task.getDescription(), String.valueOf(task.getStartTime()),
                String.valueOf(task.getDuration())};
        return String.join(",", toJoin);
    }


    public static Task fromString(String value) {
        String[] parts = value.split(",");
        String taskType = parts[1];
        int id = Integer.parseInt(parts[0]);
        String title = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];

        switch (taskType) {
            case "TASK":
                return new Task(id, title, description, status);
            case "EPIC":
                return new Epic(id, title, description, status);
            case "SUBTASK":
                return new Subtask(id, title, description, status);
            default:
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
        super.createTask(task);
        save();
        return task.getId();
    }

    @Override
    public int createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic.getId();
    }

    @Override
    public int createSubTask(Subtask subTask) {
        super.createSubTask(subTask);
        save();
        return subTask.getId();
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
        Task task = super.getTaskById(taskId);
        save();
        return task;
    }

    @Override
    public Subtask getSubTaskById(int subTaskId) {
        Subtask subtask = super.getSubTaskById(subTaskId);
        save();
        return subtask;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = super.getEpicById(epicId);
        save();
        return epic;
    }
}