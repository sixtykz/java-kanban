package main.java.service;


import main.java.exceptions.ManagerSaveException;
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

        if (TaskType.TASK.toString().equals(taskType)) {
            int id = count++;
            String title = parts[2];
            Status status = Status.valueOf(parts[3]);
            String description = parts[4];

            return new Task(id, title, status, description);
        } else if (TaskType.EPIC.toString().equals(taskType)) {
            int id = count++;
            String title = parts[2];
            Status epicStatus = Status.valueOf(parts[3]);
            String description = parts[4];

            return new Epic(id, title, epicStatus, description);
        } else if (TaskType.SUBTASK.toString().equals(taskType)) {
            int id = count++;
            String title = parts[2];
            Status subTaskStatus = Status.valueOf(parts[3]);
            String description = parts[4];

            return new Subtask(id, description, title, subTaskStatus);
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("task.csv"))) {
            for (Task task : tasks.values()) {
                writer.write(task.toString());
            }
            for (Epic epic : epics.values()) {
                writer.write(epic.toString());
            }
            for (Subtask subtask : subTasks.values()) {
                writer.write(subtask.toString());
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Error saving data", e);
        }
    }

    @Override
    public int createTask(Task task) {
       return super.createTask(task);
import main.java.intefaces.HistoryManager;
import main.java.tasks.Epic;
import main.java.tasks.Status;
import main.java.tasks.Subtask;
import main.java.tasks.Task;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private String title;

    private static File file;

    public void FileBackedTaskManager(File file) {
        this.file = file;
        load();
    }

    private void load() {
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 6) {
                        int id = Integer.parseInt(data[0]);
                        String type = data[1];
                        String name = data[2];
                        Status status = Status.valueOf(data[3].toUpperCase());
                        String description = data[4];
                        String epic = data[5];

                        if (type.equals("task")) {
                            addTask(new Task(id, title, description, status));
                        } else if (type.equals("subtask")) {
                            addSubTask(new Subtask(description, name, status, id));
                        } else if (type.equals("epic")) {
                            addEpic(new Epic(description, name, status, id));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        try (PrintWriter writer = new PrintWriter(file)) {
            for (Task task : tasks.values()) {
                writer.println(task.toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
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

    @Override
    public void completeTask(int taskId) {
        super.completeTask(taskId);
        save();
    }

    @Override
    public void updateTask(int taskId, String name, Status status, String description) {
        super.updateTask(taskId, name, status, description);
        save();
    }

    @Override
    public void assignTaskToEpic(int taskId, int epicId) {
        super.assignTaskToEpic(taskId, epicId);
        save();
    }


    private int id;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public int generateId() {
        return id++;
    }

    public static File getFile() {
        return file;
    }

    public FileBackedTasksManager(File file) {
        FileBackedTasksManager.file = file;
    }


    // метод возвращает последнюю строку с просмотренными задачами из файла
    private List<String> historyFromString(File file) throws IOException {
        BufferedReader br;
        List<String> listString = new ArrayList<>();
        br = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                line = br.readLine();
                break;
            }
        }
        if (line != null && !line.isEmpty()) {
            listString = Arrays.asList(line.split(","));
        } else {
            return listString;
        }
        return listString;
    }


    /*private static String historyToString(HistoryManager manager) {
        return manager.getHistory().stream()
                .map(Task -> Task.getId().collect(Collectors.joining(","));
    }*/
    @Override
    public int createTask(Task task) {
        int newTaskId = generateId();
        task.setId(newTaskId);
        tasks.put(newTaskId, task);
        return newTaskId;
    }

    @Override
    public int createEpic(Epic epic) {
       return super.createEpic(epic);
        int newEpicId = generateId();
        epic.setId(newEpicId);
        epics.put(newEpicId, epic);
        return newEpicId;
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
        int newSubTaskId = generateId();
        subTask.setId(newSubTaskId);
        Epic epic = epics.get(subTask.getEpicId());

        if (epic != null) {
            subTasks.put(newSubTaskId, subTask);
            epic.setSubtasksList(newSubTaskId);
            updateStatusEpic(epic);
            return newSubTaskId;
        } else {
            System.out.println("Epic не найден");
            return -1;
        }
    }

    @Override
    public void deleteTaskById(int taskId) {
        super.deleteTaskById(taskId);
        save();
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
            historyManager.remove(taskId);
        } else {
            System.out.println("Task not found");
        }
    }

    @Override
    public void deleteEpicById(int epicId) {
        super.deleteEpicById(epicId);
        save();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            epics.remove(epicId);
            historyManager.remove(epicId);
        } else {
            System.out.println("Epic not found");
        }
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
        Subtask subtask = subTasks.get(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtasksList().remove((Integer) subtask.getId());
            updateStatusEpic(epic);
            historyManager.remove(subTaskId);
            subTasks.remove(subTaskId);
        } else {
            System.out.println("Subtask not found");
        }
    }


    @Override
    public void deleteAllTasks() {
        for (Integer taskId : tasks.keySet()) {
            historyManager.remove(taskId);
        }
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
        for (Integer id : epics.keySet()) {
            subTasks.remove(id);
            historyManager.remove(id);
        }
        epics.clear();

        for (Integer subTaskId : subTasks.keySet()){
            historyManager.remove(subTaskId);
        }
        subTasks.clear();
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
        for (Subtask sub : subTasks.values()) {
            tasks.remove(sub);
            historyManager.remove(sub.getId());
        }
        subTasks.clear();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubTaskById(int subTaskId) {
        Subtask subtask = subTasks.get(subTaskId);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public List<Task> getAllTask() {
        if (tasks.isEmpty()) {
            System.out.println("Task list пуст");
        }
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpic() {
        if (epics.isEmpty()) {
            System.out.println("Epic list пуст");
        }
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubTask() {
        if (subTasks.isEmpty()) {
            System.out.println("SubTask list пуст");
        }
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public List<Subtask> getAllSubTaskByEpicId(int epicId) {
        if (epics.containsKey(id)) {
            List<Subtask> subTasksNew = new ArrayList<>();
            Epic epic = epics.get(id);
            for (int i = 0; i < epic.getSubtasksList().size(); i++) {
                subTasksNew.add(subTasks.get(epic.getSubtasksList().get(i)));
            }
            return subTasksNew;
        } else {
            System.out.println("SubTask list пуст");
        }
        return null;
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Task не найден");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Task не найден");
        }
    }

    private void updateStatusEpic(Epic epic) {
        if (epic.getSubtasksList().isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            int countDone = 0;
            int countNew = 0;

            List<Integer> subTaskIds = epic.getSubtasksList();
            for (Integer subTaskId: subTaskIds){
                for (int i = 0; i < epic.getSubtasksList().size(); i++) {
                    Subtask subTask = subTasks.get(subTaskId);


                    if (subTask.getStatus() == Status.DONE) {
                        countDone++;
                    }
                    if (subTask.getStatus() == Status.NEW) {
                        countNew++;
                    }
                    if (subTask.getStatus() == Status.IN_PROGRESS) {
                        epic.setStatus(Status.IN_PROGRESS);
                        return;
                    }
                }
            }

            if (countDone == epic.getSubtasksList().size()) {
                epic.setStatus(Status.DONE);
            } else if (countNew == epic.getSubtasksList().size()) {
                epic.setStatus(Status.NEW);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    @Override
    public void updateSubtask(Subtask subTask) {
        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            updateStatusEpic(epic);
        } else {
            System.out.println("SubTask не найден");
        }
    }


    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


    @Override
    public void printTasks() {
        if (tasks.isEmpty()) {
            System.out.println("Task List пуст");
            return;
        }
        for (Task task : tasks.values()) {
            System.out.println("Task{" +
                    "description='" + task.getDescription() + '\'' +
                    ", id=" + task.getId() +
                    ", name='" + task.getTitle() + '\'' +
                    ", status=" + task.getStatus() +
                    '}');
        }
    }

    @Override
    public void printEpics() {
        if (epics.isEmpty()) {
            System.out.println("Epic List пуст");
            return;
        }
        for (Epic epic : epics.values()) {
            System.out.println("Epic{" +
                    "subtasksIds=" + epic.getSubtasksList() +
                    ", description='" + epic.getDescription() + '\'' +
                    ", id=" + epic.getId() +
                    ", name='" + epic.getTitle() + '\'' +
                    ", status=" + epic.getStatus() +
                    '}');
        }
    }

    @Override
    public void printSubTask() {
        if (subTasks.isEmpty()) {
            System.out.println("SubTask List пуст");
            return;
        }
        for (Subtask subTask : subTasks.values()) {
            System.out.println("Subtask{" +
                    "epicId=" + subTask.getEpicId() +
                    ", description='" + subTask.getDescription() + '\'' +
                    ", id=" + subTask.getId() +
                    ", name='" + subTask.getTitle() + '\'' +
                    ", status=" + subTask.getStatus() +
                    '}');
        }
    }
}