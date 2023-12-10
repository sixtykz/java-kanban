package main.java.service;

import main.java.intefaces.HistoryManager;
import main.java.intefaces.TaskManager;
import main.java.tasks.Epic;
import main.java.tasks.Status;
import main.java.tasks.Subtask;
import main.java.tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    IdCounter idCounter = new IdCounter();
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void addTask(Task task) {
        if (task.getId() == 0) {
            task.setId(idCounter.getId());
            if (task.getStatus().equals(Status.NEW) || task.getStatus().equals(Status.DONE) || task.getStatus().equals(
                    Status.IN_PROGRESS)) {
                tasks.put(task.getId(), task);
                System.out.println("Задача успешно добавлена");
            } else {
                System.out.println("Проверьте корректность значения статуса задачи: " + task.getTitle());
            }
        }
    }

    @Override
    public void addEpic(Epic epic) {
        if (epic.getId() == 0) {
            epic.setId(idCounter.getId());
            if (epic.getStatus().equals(Status.NEW) || epic.getStatus().equals(Status.DONE) || epic.getStatus().equals(Status.IN_PROGRESS)) {
                epics.put(epic.getId(), epic);
                System.out.println("Епик успешно добавлен");
            } else {
                System.out.println("Проверьте корректность значения статуса епика: " + epic.getTitle());
            }
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (subtask.getId() == 0) {
            subtask.setId(idCounter.getId());
            if (epics.containsKey(subtask.getEpicId())) {
                if (subtask.getStatus().equals(Status.NEW) || subtask.getStatus().equals(Status.DONE) || subtask.getStatus().equals(Status.IN_PROGRESS)) {
                    subtasks.put(subtask.getId(), subtask);
                    epics.get(subtask.getEpicId()).getSubtasksList().add(subtask.getId());
                    System.out.println("Подзадача успешно добавлена");
                    updateEpicStatus(epics.get(subtask.getEpicId()).getId());
                } else {
                    System.out.println("Проверьте корректность значения статуса подзадачи: " + subtask.getTitle());
                }

            }
        }
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> list = new ArrayList<>();
        for (Integer id : tasks.keySet()) {
            list.add(tasks.get(id));
        }
        return list;
    }

    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> list = new ArrayList<>();
        for (Integer id : epics.keySet()) {
            list.add(epics.get(id));
        }
        return list;
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        ArrayList<Subtask> list = new ArrayList<>();
        for (Integer id : subtasks.keySet()) {
            list.add(subtasks.get(id));
        }
        return list;
    }

    @Override
    public void taskClean() {
        tasks.clear();
        System.out.println("Удаление задач выполнено");
    }

    @Override
    public void epicClean() {
        subtasks.clear();
        epics.clear();
        System.out.println("Удаление эпиков выполнено");
    }

    @Override
    public void subtaskClean() {
        int subtaskCleanCounter = 0;
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.cleanSubtaskIds();
            subtaskCleanCounter++;
        }
        if (epics.size() == subtaskCleanCounter) {
            System.out.println("Удаление задач выполнено");
        }
        for (Epic epic : epics.values()) {
            updateEpicStatus(epic.getId());
            System.out.println("Обновление статуса эпиков выполнено");
        }
    }

    public Task getTaskById(int idInput) {
        Task task = null;
        if (tasks.get(idInput) != null) {
            historyManager.add(tasks.get(idInput));
            task = tasks.get(idInput);
        }
        return task;
    }

    public Epic getEpicById(int idInput) {
        Epic epic = null;
        if (epics.get(idInput) != null) {
            historyManager.add(epics.get(idInput));
            epic = epics.get(idInput);
        }
        return epic;
    }

    public Subtask getSubtaskById(int idInput) {
        Subtask subtask = null;
        if (subtasks.get(idInput) != null) {
            historyManager.add(subtasks.get(idInput));
            subtask = subtasks.get(idInput);
        }
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        final int id = task.getId();
        for (Task t : tasks.values()) {
            if (t.getId() == id) {
                tasks.put(t.getId(), task);
                System.out.println("Обновление задачи прошло успешно");
            }
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        final int id = epic.getId();
        for (Epic e : epics.values()) {
            if (e.getId() == id) {
                epics.put(e.getId(), epic);
                System.out.println("Обновление прошло успешно");
            }
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        final int id = subtask.getId();
        final int epicId = subtask.getEpicId();
        if (epics.containsKey(epicId) && subtasks.containsKey(id)) {
            // ней эпик,
            // существуют."
            subtasks.put(id, subtask);
            System.out.println("Обновление прошло успешно");
            updateEpicStatus(epicId);
        }
    }

    @Override
    public void removeTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            System.out.println("Задача удалена");
        }
    }

    @Override
    public void removeEpicById(int id) {
        ArrayList<Integer> al;
        al = epics.get(id).getSubtasksList();
        for (Subtask subtask : subtasks.values()) {
            for (int i = 0; i < al.size(); i++) {
                if (subtask.getId() == al.get(i)) {
                    subtasks.remove(subtask.getEpicId());
                }
            }
        }
        if (epics.containsKey(id)) {
            epics.remove(id);
            System.out.println("Эпик удалён");
        }

    }

    @Override
    public void removeSubtaskById(int id) {
        if (epics.containsKey(subtasks.get(id).getEpicId())) {
            epics.get(subtasks.get(id).getEpicId()).removeSubtask(id);
            System.out.println("Удаление прошло успешно");
            updateEpicStatus(subtasks.get(id).getEpicId());
        }
        if (subtasks.containsKey(id)) {
            subtasks.remove(id);
            System.out.println("Подзадача удалена");
        }
    }

    @Override
    public void changeStatusTask(int id, Status status) {
        if (tasks.containsKey(id)) {
            tasks.get(id).setStatus(status);
            System.out.println("Статус изменён");
        } else {
            System.out.println("Задача с таким идентификатором не найдена");
        }
    }

    @Override
    public void changeStatusSubtask(int id, Status status) {
        if (subtasks.containsKey(id)) {
            subtasks.get(id).setStatus(status);
            System.out.println("Статус изменён");
            updateEpicStatus(subtasks.get(id).getEpicId());
        } else {
            System.out.println("Подзадача с таким идентификатором не найдена");
        }
    }

    @Override
    public ArrayList<Integer> getSubtaskList(int epicId) {
        for (Integer id : epics.keySet()) {
            if (epics.get(id).getId() == epicId) {
                return epics.get(id).getSubtasksList();
            }
        }
        return null;
    }

    @Override
    public void updateEpicStatus(int epicId) {
        if (epics.containsKey(epicId)) {
            boolean inProgress = true;
            if (epics.get(epicId).getSubtasksList().isEmpty()) {
                epics.get(epicId).setStatus(Status.NEW);
                inProgress = false;
            }
            if (!epics.get(epicId).getSubtasksList().isEmpty()) {
                int counterNew = 0;
                for (int i = 0; i < epics.get(epicId).getSubtasksList().size(); i++) {
                    if (subtasks.get(epics.get(epicId).getSubtasksList().get(i)).getStatus().equals(Status.NEW)) {
                        counterNew++;
                    }
                }
                if (epics.get(epicId).getSubtasksList().size() == counterNew) {
                    epics.get(epicId).setStatus(Status.NEW);
                    inProgress = false;
                }
            }
            if (!epics.get(epicId).getSubtasksList().isEmpty()) {
                int counterDone = 0;
                for (int i = 0; i < epics.get(epicId).getSubtasksList().size(); i++) {
                    if (subtasks.get(epics.get(epicId).getSubtasksList().get(i)).getStatus().equals(Status.DONE)) {
                        counterDone++;
                    }
                }
                if (epics.get(epicId).getSubtasksList().size() == counterDone) {
                    epics.get(epicId).setStatus(Status.DONE);
                    inProgress = false;
                }
                if (inProgress) {
                    epics.get(epicId).setStatus(Status.IN_PROGRESS);
                }
                epics.put(epics.get(epicId).getId(), epics.get(epicId));
                System.out.println("Обновление списка эпика прошло успешно");
            }
        } else {
            System.out.println("Епик с таким id не найден");
        }
    }

    public List<Task> historyList() {
        List<Task> list = new LinkedList<>();
        for (Task task : historyManager.getHistory()) {
            list.add(task);
        }
        return list;
    }
}