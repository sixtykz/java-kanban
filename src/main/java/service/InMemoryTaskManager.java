package main.java.service;

import main.java.intefaces.HistoryManager;
import main.java.intefaces.TaskManager;
import main.java.tasks.Epic;
import main.java.tasks.Status;
import main.java.tasks.Subtask;
import main.java.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private int id;

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public int generateId() {
        return id++;
    }

    @Override
    public int createTask(Task task) {
        int newTaskId = generateId();
        task.setId(newTaskId);
        tasks.put(newTaskId, task);
        return newTaskId;
    }

    @Override
    public int createEpic(Epic epic) {
        int newEpicId = generateId();
        epic.setId(newEpicId);
        epics.put(newEpicId, epic);
        return newEpicId;
    }

    @Override
    public int createSubTask(Subtask subTask) {
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
        if (tasks.containsKey(id)) {
                tasks.remove(id);
                historyManager.remove(taskId);
        } else {
            System.out.println("Task not found");
        }
    }

    @Override
    public void deleteEpicById(int epicId) {
        Epic epic = epics.get(id);
        if (epic != null) {
                epics.remove(id);
                historyManager.remove(id);
        } else {
            System.out.println("Epic not found");
        }
    }

    @Override
    public void deleteSubTaskById(int subTaskId) {
        Subtask subtask = subTasks.get(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtasksList().remove((Integer) subtask.getId());
            updateStatusEpic(epic);
                historyManager.remove(id);
                subTasks.remove(id);
        } else {
            System.out.println("Subtask not found");
        }
    }


    @Override
    public void deleteAllTasks() {
        for (Integer id : tasks.keySet()) {
            tasks.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteAllEpics() {
        for (Integer id : tasks.keySet()) {
            subTasks.clear();
            epics.clear();
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteAllSubTask() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasksList().clear();
            updateStatusEpic(epic);
            subTasks.remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = null;
        if (tasks.get(id) != null) {
            historyManager.add(tasks.get(id));
            task = tasks.get(id);
    }
        return task;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = null;
        if (epics.get(id) != null) {
            historyManager.add(epics.get(id));
            epic = epics.get(id);
        }
        return epic;
    }

    @Override
    public Subtask getSubTaskById(int subTaskId) {
        Subtask subtask = null;
        if (subTasks.get(id) != null) {
            historyManager.add(subTasks.get(id));

            subtask = subTasks.get(id);
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